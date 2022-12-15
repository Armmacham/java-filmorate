package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class FilmStorage {
    public Map<Integer, Film> films = new HashMap<>();

    public static int id;

    public int generateId() {
        return ++id;
    }

    public Film add(Film film) {
        int newId = generateId();
        film.setId(newId);
        films.put(newId, film);
        return film;
    }

    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    public int getId() {
        return id;
    }
}