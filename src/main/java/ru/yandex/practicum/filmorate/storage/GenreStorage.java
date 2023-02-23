package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreStorage {
    Genre getGenreById(int id);
    List<Genre> getAllGenres();
    List<Genre> getGenresByFilmId(int filmId);
    boolean addFilmGenres(int filmId, Collection<Genre> genres);
    boolean deleteFilmGenres(int filmId);
}
