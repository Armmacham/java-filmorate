package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Genre getGenreById(int id);
    Collection<Genre> getAllGenres();
    Collection<Genre> getGenresByFilmId(int filmId);
    boolean addFilmGenres(int filmId, Collection<Genre> genres);
    boolean deleteFilmGenres(int filmId);
}
