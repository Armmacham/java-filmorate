package ru.yandex.practicum.filmorate.storage.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


@Component("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film getFilmById(Integer filmId) {

        String sqlFilm = "select * from FILM " +
                "INNER JOIN RATINGMPA R on FILM.RATING_ID = R.RATING_ID " +
                "where FILM_ID = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlFilm, (rs, rowNum) -> makeFilm(rs), filmId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Фильм с идентификатором " +
                    filmId + " не зарегистрирован!");
        }
        log.info("Найден фильм: {} {}", film.getId(), film.getName());
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "select * from FILM " +
                "INNER JOIN RATINGMPA R on FILM.RATING_ID = R.RATING_ID ";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> makeFilm(resultSet));
    }

    @Override
    public Integer addFilm(Film film) {
        String sqlQuery = "insert into FILM " +
                "(NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING, RATING_ID) " +
                "values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            preparedStatement.setLong(4, film.getDuration());
            preparedStatement.setInt(5, film.getRating());
            preparedStatement.setInt(6, Math.toIntExact(film.getMpa().getId()));
            return preparedStatement;
        }, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return id;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update FILM " +
                "set NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING = ? ,RATING_ID = ? " +
                "where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating(),
                film.getMpa().getId(),
                film.getId());

        if (film.getLikesCount() != null) {
            for (Integer userId : film.getLikesCount()) {
                addLike(film.getId(), userId);
            }
        }
        return getFilmById(film.getId());
    }

    @Override
    public boolean deleteFilm(Integer id) {
        String sqlQuery = "delete from FILM where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
        return true;
    }

    @Override
    public boolean addLike(int filmId, int userId) {
        String sql = "select * from LIKES where USER_ID = ? and FILM_ID = ?";
        String setLike = "insert into LIKES (USER_ID, FILM_ID) values (?, ?) ";
        jdbcTemplate.update(setLike, userId, filmId);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, userId, filmId);
        log.info(String.valueOf(sqlRowSet.next()));
        return sqlRowSet.next();
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        String deleteLike = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(deleteLike, filmId, userId);
        return true;
    }

    @Override
    public Collection<Film> getMostPopularFilms(int count) {
        String sqlMostPopular = "select count(L.LIKE_ID) as likeRating" +
                ",FILM.FILM_ID" +
                ",FILM.NAME ,FILM.DESCRIPTION ,RELEASE_DATE ,DURATION ,RATING ,R.RATING_ID, R.NAME, R.DESCRIPTION from FILM " +
                "left join LIKES L on L.FILM_ID = FILM.FILM_ID " +
                "inner join RATINGMPA R on R.RATING_ID = FILM.RATING_ID " +
                "group by FILM.FILM_ID " +
                "ORDER BY likeRating desc " +
                "limit ?";
        return jdbcTemplate.query(sqlMostPopular, (rs, rowNum) -> makeFilm(rs), count);
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        int filmId = resultSet.getInt("film_id");
        return new Film(
                filmId,
                resultSet.getString("name"),
                resultSet.getString("description"),
                Objects.requireNonNull(resultSet.getDate("release_date")).toLocalDate(),
                resultSet.getInt("duration"),
                resultSet.getInt("rating"),
                new Mpa(resultSet.getInt("RatingMPA.rating_id"),
                        resultSet.getString("RatingMPA.name"),
                        resultSet.getString("RatingMPA.description")),
                List.of(),
                getFilmLikes(filmId));
    }

    private Set<Integer> getFilmLikes(int filmId) {
        String sqlGetLikes = "select USER_ID from LIKES where FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.queryForList(sqlGetLikes, Integer.class, filmId));
    }
}