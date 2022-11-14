package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
@Service
@Repository
public class FilmService {
    private final FilmStorage filmStorage;
    private static final LocalDate START_DATA = LocalDate.of(1895, 12, 28);

    public Map<Integer, Film> getAllFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public void validateReleaseDate(Film film, String text) {
        if (film.getReleaseDate().isBefore(START_DATA)) {
            throw new ValidationException("Дата релиза не может быть раньше " + START_DATA);
        }
        log.debug("{} фильм: {}", text, film.getName());
    }
}
