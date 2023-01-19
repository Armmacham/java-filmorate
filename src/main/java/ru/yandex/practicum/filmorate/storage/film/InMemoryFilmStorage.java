package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UsersLikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    public Map<Integer, Film> films = new HashMap<>();

    public static int id;

    public int generateId() {
        return ++id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Film addFilm(Film film) {
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistsException(String.format("Фильм с id номером %d уже существует", film.getId()));
        }
        int newId = generateId();
        film.setId(newId);
        films.put(newId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id номером %d не найден", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> list = new ArrayList<>(films.values());
        return list;
    }

    @Override
    public Film getFilmById(Integer id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id номером %d не найден", id));
        }
        return films.get(id);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        getFilmById(filmId).addLike(userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        Film film = films.get(filmId);
        if (!film.getLikesCount().contains(userId)) {
            throw new UsersLikeNotFoundException(String.format("Лайк пользователя с id номером %d не найден", userId));
        }
        film.removeLike(userId);
    }

    @Override
    public List<Film> getTopTenRatedFilms(Integer count) {
        return getAllFilms()
                .stream()
                .filter(film -> film.getLikesCount() != null)
                .sorted((t1, t2) -> t2.getLikesCount().size() - t1.getLikesCount().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}