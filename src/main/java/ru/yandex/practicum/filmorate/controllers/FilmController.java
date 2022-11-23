package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.services.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Фильм {} добавлен", film);
        return filmService.addFilm(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("Количество фильмов: {}", filmService.getAllFilms());
        return filmService.getAllFilms();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Фильм {} обновлён", film);
        return filmService.updateFilm(film);
    }
}
