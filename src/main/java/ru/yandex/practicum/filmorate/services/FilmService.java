package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private static final LocalDate START_DATA = LocalDate.of(1895, 12, 28);

    public Film addFilm(Film film) {
        if (filmStorage.getFilms().containsKey(film.getId())) {
            throw new RuntimeException("Такой фильм уже существует");
        }
        validateReleaseDate(film, "Создан фильм");
        return filmStorage.add(film);
    }

    public List<Film> getAllFilms() {
        List<Film> filmsList = new ArrayList<>(filmStorage.getFilms().values());
        return filmsList;
    }

    public Film updateFilm(Film film) {
        if (!filmStorage.getFilms().containsKey(film.getId())) {
            throw new RuntimeException("Такого фильма нет в хранилище");
        }
        validateReleaseDate(film, "Обновлены данные фильма");
        return filmStorage.update(film);
    }

    public void validateReleaseDate(Film film, String text) {
        if (film.getReleaseDate().isBefore(START_DATA)) {
            throw new ValidationException("Дата релиза не может быть раньше " + START_DATA);
        }
        log.debug("{}: {}", text, film.getName());
    }

}
