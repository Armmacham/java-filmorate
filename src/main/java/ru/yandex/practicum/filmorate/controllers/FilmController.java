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
        log.info("Film added");
        return filmService.addFilm(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        List<Film> filmsList= new ArrayList<>(filmService.getFilms().values());
        log.debug("Количество фильмов: {}", filmsList.size());
        return filmsList;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Film updated");
        return filmService.updateFilm(film);
    }
}
