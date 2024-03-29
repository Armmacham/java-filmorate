package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Integer addFilm(Film film);

    Film update(Film film);

    Film getFilmById(Integer filmId);

    List<Film> getAllFilms();

    boolean deleteFilm(Integer id);

    boolean addLike(int filmId, int userId);

    boolean deleteLike(int filmId, int userId);

    Collection<Film> getMostPopularFilms(int size);
}
