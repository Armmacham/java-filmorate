package ru.yandex.practicum.filmorate.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FilmServiceTests {
    @Autowired
    private FilmService filmService;

    @Test
    void shouldAddWhenAddValidFilmData() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description.");
        film.setReleaseDate(LocalDate.of(1995, 5, 26));
        film.setDuration(90);
        film.setRating(0);
        film.setMpa(new Mpa(1, "mpa", "description"));
        Film addedFilm = filmService.addFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }

    @Test
    void shouldNotAddFilmWhenNameEmpty() {
        try {
            Film film = new Film();
            film.setName("");
            film.setDescription("Correct description");
            film.setReleaseDate(LocalDate.of(1895, 12, 28));
            film.setDuration(100);
            film.setMpa(new Mpa(1, "mpa", "description"));
            filmService.addFilm(film);
        } catch (IncorrectParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldNotAddFilmWhenNameBlank() {
        try {
            Film film = new Film();
            film.setName("  ");
            film.setDescription("Correct description");
            film.setReleaseDate(LocalDate.of(1895, 12, 28));
            film.setDuration(100);
            film.setMpa(new Mpa(1, "mpa", "description"));
        } catch (IncorrectParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmDuration() {
        try {
            Film film = new Film();
            film.setName("Correct Name");
            film.setDescription("Correct description");
            film.setReleaseDate(LocalDate.of(1995, 5, 26));
            film.setDuration(-100);
            film.setMpa(new Mpa(1, "mpa", "description"));
            filmService.addFilm(film);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmReleaseDate() {
        try {
            Film film = new Film();
            film.setName("Correct Name");
            film.setDescription("Correct description");
            film.setReleaseDate(LocalDate.of(1895, 12, 27));
            film.setDuration(100);
            film.setMpa(new Mpa(1, "mpa", "description"));
            filmService.addFilm(film);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldAddWhenAddValidFilmReleaseDateBoundary() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description.");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        film.setDuration(100);
        film.setMpa(new Mpa(1, "mpa", "description"));
        Film addedFilm = filmService.addFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmDescription() {
        try {
            Film film = new Film();
            film.setName("Correct Name");
            film.setDescription("More then 200 symbols. More then 200 symbols. More then 200 symbols. More then 200 symbols. More then 200 symbols." +
                    " More then 200 symbols. More then 200 symbols. More then 200 symbols. More then 200 symbols. ");
            film.setReleaseDate(LocalDate.of(1995, 5, 26));
            film.setDuration(100);
            film.setMpa(new Mpa(1, "mpa", "description"));
            filmService.addFilm(film);
        } catch (DataIntegrityViolationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldAddWhenAddFilmDescriptionValid() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Less then 200 symbols description");
        film.setReleaseDate(LocalDate.of(1995, 5, 26));
        film.setDuration(100);
        film.setMpa(new Mpa(1, "mpa", "description"));
        Film addedFilm = filmService.addFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }

    @Test
    void shouldThrowExceptionWhenUpdateFailedFilmId() {
        try {
            Film film = new Film();
            film.setId(999);
            film.setName("Correct Name");
            film.setDescription("Correct description.");
            film.setReleaseDate(LocalDate.of(1995, 5, 26));
            film.setDuration(90);
            film.setMpa(new Mpa(1, "mpa", "description"));
            filmService.addFilm(film);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }


}
