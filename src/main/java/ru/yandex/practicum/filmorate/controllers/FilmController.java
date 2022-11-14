package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.services.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (filmService.getAllFilms().containsKey(film.getId())) {
            throw new RuntimeException("Такой фильм уже существует");
        }
        filmService.validateReleaseDate(film, "Фильм добавлен");
        return filmService.addFilm(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        List<Film> filmsList= new ArrayList<>(filmService.getAllFilms().values());
        log.debug("Количество фильмов: {}", filmsList.size());
        return filmsList;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (!filmService.getAllFilms().containsKey(film.getId())) {
            throw new RuntimeException("Такого фильма нет в хранилище");
        }
        filmService.validateReleaseDate(film, "Данные фильма обновлены");
        return filmService.updateFilm(film);
    }
}
