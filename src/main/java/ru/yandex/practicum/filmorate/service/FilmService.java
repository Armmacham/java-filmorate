package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    private final GenreService genreService;
    private final Validator validator;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage, @Autowired(required = false) UserService userService, GenreService genreService, Validator validator) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreService = genreService;
        this.validator = validator;
    }

    public Film addFilm(Film film) {
//        if (validator.validate(film).size() > 0 || validator.validate(film.getGenres()).size() > 0) {
//            throw new ValidationException();
//        }
        Integer film1 = filmStorage.addFilm(film);
        genreService.addFilmGenres(film1, film.getGenres());
        Film filmById = filmStorage.getFilmById(film1);
        filmById.setGenres(genreService.getFilmGenres(film1));
        return filmById;
    }

    public List<Film> getAllFilms() {
        List<Film> allFilms = filmStorage.getAllFilms();
        allFilms.forEach(f -> f.setGenres(genreService.getFilmGenres(f.getId())));
        return allFilms;
    }

    public Film updateFilm(Film film) {
        Film updated = filmStorage.update(film);
        genreService.deleteFilmGenres(film.getId());
        if (!film.getGenres().isEmpty()) {
            genreService.addFilmGenres(film.getId(), film.getGenres());
        }
        updated.setGenres(genreService.getFilmGenres(updated.getId()));
        return updated;
    }

    public Film getFilmById(Integer filmId) {
        Film filmById = filmStorage.getFilmById(filmId);
        filmById.setGenres(genreService.getFilmGenres(filmById.getId()));
        return filmById;
    }

    public void addLike(Integer filmId, Integer userId) {
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
                .peek(film -> genreService.getFilmGenres(film.getId()))
                .sorted((t1, t2) -> t2.getLikesCount().size() - t1.getLikesCount().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}

