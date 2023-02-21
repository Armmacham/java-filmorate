package ru.yandex.practicum.filmorate.daoTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTests {

    private final FilmDbStorage filmDbStorage;

    @Test
    public void shouldGetFilmById() {

        Film film = new Film(1, "Film", "Description",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "o", "o"),
                new ArrayList<>(),
                new ArrayList<>());

        Film film1 = filmDbStorage.addFilm(film);

        Film dbFilm = filmDbStorage.getFilmById(film1.getId());
        assertThat(dbFilm).hasFieldOrPropertyWithValue("id", film1.getId());
        filmDbStorage.deleteFilm(dbFilm);
    }

    @Test
    void shouldGetAllFilms() {
        Film film_1 = new Film(1, "Film_1", "Description_1",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "Name", "Description"),
                new ArrayList<>(),
                new ArrayList<>());

        Film film_2 = new Film(2, "Film_2", "Description_2",
                LocalDate.now().minusYears(15),
                100,
                2,
                new Mpa(3, "o", "o"),
                new ArrayList<>(),
                new ArrayList<>());

        Film film1_added = filmDbStorage.addFilm(film_1);
        Film film2_added = filmDbStorage.addFilm(film_2);

        Collection<Film> dbFilms = filmDbStorage.getAllFilms();
        assertEquals(2, dbFilms.size());
        filmDbStorage.deleteFilm(film1_added);
        filmDbStorage.deleteFilm(film2_added);
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film(1, "Film", "Description",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "o", "o"),
                new ArrayList<>(),
                new ArrayList<>());

        Film added = filmDbStorage.addFilm(film);
        added.setName("update");
        filmDbStorage.update(added);
        Film dbFilm = filmDbStorage.getFilmById(added.getId());
        assertThat(dbFilm).hasFieldOrPropertyWithValue("name", "update");
        filmDbStorage.deleteFilm(added);
    }

    @Test
    void shouldDeleteFilm() {
        Film film_1 = new Film(1, "Film_1", "Description_1",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "Name", "Description"),
                new ArrayList<>(),
                new ArrayList<>());

        Film film_2 = new Film(2, "Film_2", "Description_2",
                LocalDate.now().minusYears(15),
                100,
                2,
                new Mpa(3, "o", "o"),
                new ArrayList<>(),
                new ArrayList<>());

        Film film1 = filmDbStorage.addFilm(film_1);
        Film film2 = filmDbStorage.addFilm(film_2);

        Collection<Film> beforeDelete = filmDbStorage.getAllFilms();
        filmDbStorage.deleteFilm(film1);
        Collection<Film> afterDelete = filmDbStorage.getAllFilms();
        assertEquals(beforeDelete.size() - 1, afterDelete.size());
        filmDbStorage.deleteFilm(film2);
    }
}
