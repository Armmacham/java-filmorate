package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getMpaById(int mpaId) {
        String sqlMpa = "select * from RATINGMPA where RATING_ID = ?";
        Mpa mpa;
        try {
            mpa = jdbcTemplate.queryForObject(sqlMpa, this::makeMpa, mpaId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Возрастной рейтинг с идентификатором " +
                    mpaId + " не зарегистрирован!");
        }
        return mpa;
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        String sqlMpa = "select * from RATINGMPA";
        return jdbcTemplate.query(sqlMpa, this::makeMpa);
    }
    
    

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(resultSet.getInt("rating_id"),
                resultSet.getString("name"),
        resultSet.getString("description"));
    }
}