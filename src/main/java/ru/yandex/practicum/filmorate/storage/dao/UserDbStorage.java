package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("UserDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(Integer id) {
        String sqlUser = "select * from USERS where USER_ID = ?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(sqlUser, (rs, rowNum) -> makeUser(rs), id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Пользователь с идентификатором " +
                    id + " не зарегистрирован!");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sqlAllUsers = "select * from USERS";
        return jdbcTemplate.query(sqlAllUsers, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public int addUser(User user) {
        String sqlQuery = "insert into USERS " +
                "(EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "values (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public User updateUser(User user) {
        String sqlUser = "update USERS set " +
                "EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "where USER_ID = ?";
        jdbcTemplate.update(sqlUser,
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());

        return getUserById(user.getId());
    }

    @Override
    public boolean deleteUserById(int id) {
        String sqlQuery = "delete from USERS where USER_ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("user_id");
        return new User(
                userId,
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("name"),
                Objects.requireNonNull(resultSet.getDate("birthday")).toLocalDate(),
                getUserFriends(userId).stream().map(User::getId).collect(Collectors.toList()));
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        String sql = "select u.* " +
                "from FRIENDSHIP f " +
                "         inner join USERS U on U.USER_ID = f.FRIEND_ID " +
                "WHERE f.USER_ID = ?;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);
    }

    @Override
    public boolean addFriend(int userId, int friendId) {
        boolean friendAccepted;
        String sqlGetReversFriend = "select * from FRIENDSHIP " +
                "where USER_ID = ? and FRIEND_ID = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlGetReversFriend, friendId, userId);
        friendAccepted = sqlRowSet.next();
        String sqlSetFriend = "insert into FRIENDSHIP (USER_ID, FRIEND_ID, STATUS) " +
                "VALUES (?,?,?)";
        jdbcTemplate.update(sqlSetFriend, userId, friendId, friendAccepted);
        if (friendAccepted) {
            String sqlSetStatus = "update FRIENDSHIP set STATUS = true " +
                    "where USER_ID = ? and FRIEND_ID = ?";
            jdbcTemplate.update(sqlSetStatus, friendId, userId);
        }
        return true;
    }

    @Override
    public boolean deleteFriend(int userId, int friendId) {
        String sqlDeleteFriend = "delete from FRIENDSHIP where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlDeleteFriend, userId, friendId);
        String sqlSetStatus = "update FRIENDSHIP set STATUS = false " +
                "where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlSetStatus, friendId, userId);
        return true;
    }

}