package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private static final LocalDate START_DATA = LocalDate.of(1895, 12, 28);

    public Film addFilm(Film film) {
        validateReleaseDate(film, "Создан фильм");
        return filmStorage.addFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film updateFilm(Film film) {
        validateReleaseDate(film, "Обновлены данные фильма");
        return filmStorage.update(film);
    }

    public Film getFilmById(Integer filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        userStorage.getUserById(userId);
        filmStorage.addLike(filmId, userId);
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, filmId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        userStorage.getUserById(userId);
        filmStorage.removeLike(filmId, userId);
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, filmId);
    }

    public List<Film> getTopRatedFilms(Integer count) {
        return filmStorage.getTopTenRatedFilms(count);
    }

    public void validateReleaseDate(Film film, String text) {
        if (film.getReleaseDate().isBefore(START_DATA)) {
            throw new ValidationException("Дата релиза не может быть раньше " + START_DATA);
        }
        log.debug("{}: {}", text, film.getName());
    }
}
