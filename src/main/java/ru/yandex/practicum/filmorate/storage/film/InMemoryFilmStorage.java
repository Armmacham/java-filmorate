package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    public static int id;

    public int generateId() {
        return ++id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Integer addFilm(Film film) {
        if (films.containsKey(film.getId())) {
            throw new EntityAlreadyExistsException(String.format("Фильм с id номером %d уже существует", film.getId()));
        }
        int newId = generateId();
        film.setId(newId);
        films.put(newId, film);
        return film.getId();
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new EntityNotFoundException(String.format("Фильм с id номером %d не найден", film.getId()));
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
            throw new EntityNotFoundException(String.format("Фильм с id номером %d не найден", id));
        }
        return films.get(id);
    }

    @Override
    public boolean deleteFilm(Integer id) {
        films.remove(id);
        return true;
    }

    @Override
    public boolean addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.addLike(userId);
        update(film);
        return true;
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.removeLike(userId);
        update(film);
        return true;
    }

    @Override
    public Collection<Film> getMostPopularFilms(int size) {
        Collection<Film> mostPopularFilms = getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikesCount().size() - f1.getLikesCount().size())
                .limit(size)
                .collect(Collectors.toCollection(HashSet::new));
        return mostPopularFilms;
    }
}