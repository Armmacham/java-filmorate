package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Getter
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
            throw new FilmAlreadyExistsException(String.format("Фильм с id номером d% уже существует", film.getId()));
        }
        int newId = generateId();
        film.setId(newId);
        films.put(newId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id номером d% не найден", film.getId()));
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
    public Film getFilmById(Integer filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(String.format("Фильм с id номером d% не найден", filmId));
        }
        return films.get(filmId);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        getFilmById(filmId).addLike(userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        if (!getFilmById(filmId).getLikesCount().contains(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с id номером d% не ставил лайк", userId));
        }
        getFilmById(filmId).removeLike(userId);
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