package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.*;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean deleteFilmGenres(int filmId) {
        String deleteOldGenres = "delete from GENRELINE where FILM_ID = ?";
        jdbcTemplate.update(deleteOldGenres, filmId);
        return true;
    }

    @Override
    public boolean addFilmGenres(int filmId, Collection<Genre> genres) {
        for (Genre genre : genres) {
            String setNewGenres = "insert into GENRELINE (FILM_ID, GENRE_ID) values (?, ?) ON CONFLICT DO NOTHING;";
            jdbcTemplate.update(setNewGenres, filmId, genre.getId());
        }
        return true;
    }

    @Override
    public List<Genre> getGenresByFilmId(int filmId) {
        String sqlGenre = "select GENRE.GENRE_ID, NAME from GENRE " +
                "INNER JOIN GENRELINE GL on GENRE.GENRE_ID = GL.GENRE_ID " +
                "where FILM_ID = ?";
        return jdbcTemplate.query(sqlGenre, this::makeGenre, filmId);
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlGenre = "select GENRE_ID, NAME from GENRE ORDER BY GENRE_ID";
        return jdbcTemplate.query(sqlGenre, this::makeGenre);
    }

    @Override
    public Genre getGenreById(int genreId) {
        String sqlGenre = "select * from GENRE where GENRE_ID = ?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(sqlGenre, this::makeGenre, genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Жанр с идентификатором " +
                    genreId + " не зарегистрирован!");
        }
        return genre;
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(resultSet.getInt("genre_id"), resultSet.getString("name"));
    }
}