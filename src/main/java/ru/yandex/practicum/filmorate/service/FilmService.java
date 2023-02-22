package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage, @Autowired(required = false) UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    private static final LocalDate START_DATA = LocalDate.of(1895, 12, 28);

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilmById(Integer filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        userService.getUserById(userId);
        getFilmById(filmId).addLike(userId);
        filmStorage.addLike(filmId, userId);
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, filmId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (!film.getLikesCount().contains(userId)) {
            throw new EntityNotFoundException(String.format("Лайк пользователя с id номером %d не найден", userId));
        }
        film.removeLike(userId);
        filmStorage.deleteLike(filmId, userId);
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, filmId);
    }

    public List<Film> getTopRatedFilms(Integer count) {
        return getAllFilms()
                .stream()
                .filter(film -> film.getLikesCount() != null)
                .sorted((t1, t2) -> t2.getLikesCount().size() - t1.getLikesCount().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}

