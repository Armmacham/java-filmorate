package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getTopTenRatedFilms(Integer count);
    Film addFilm(Film film);

    Film update(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Integer id);

    void addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);
}
