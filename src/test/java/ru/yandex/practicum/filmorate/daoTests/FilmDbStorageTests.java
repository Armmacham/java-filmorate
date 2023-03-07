package ru.yandex.practicum.filmorate.daoTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTests {

    private final FilmDbStorage filmDbStorage;

    private final UserDbStorage userDbStorage;

    @Test
    public void shouldGetFilmById() {

        Film film = new Film(1, "Film", "Description",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "o", "o"),
                new ArrayList<>(),
                new HashSet<>());

        Integer film1 = filmDbStorage.addFilm(film);

        Film dbFilm = filmDbStorage.getFilmById(film1);
        assertThat(dbFilm).hasFieldOrPropertyWithValue("id", film1);
        filmDbStorage.deleteFilm(film1);
    }

    @Test
    void shouldGetAllFilms() {
        Film film_1 = new Film(1, "Film_1", "Description_1",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "Name", "Description"),
                new ArrayList<>(),
                new HashSet<>());

        Film film_2 = new Film(2, "Film_2", "Description_2",
                LocalDate.now().minusYears(15),
                100,
                2,
                new Mpa(3, "o", "o"),
                new ArrayList<>(),
                new HashSet<>());

        Integer film1_added = filmDbStorage.addFilm(film_1);
        Integer film2_added = filmDbStorage.addFilm(film_2);

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
                new HashSet<>());

        Integer filmId = filmDbStorage.addFilm(film);
        Film addedFilm = filmDbStorage.getFilmById(filmId);
        addedFilm.setName("update");
        filmDbStorage.update(addedFilm);
        Film dbFilm = filmDbStorage.getFilmById(filmId);
        assertThat(dbFilm).hasFieldOrPropertyWithValue("name", "update");
        filmDbStorage.deleteFilm(filmId);
    }

    @Test
    void shouldDeleteFilm() {
        Film film_1 = new Film(1, "Film_1", "Description_1",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "Name", "Description"),
                new ArrayList<>(),
                new HashSet<>());

        Film film_2 = new Film(2, "Film_2", "Description_2",
                LocalDate.now().minusYears(15),
                100,
                2,
                new Mpa(3, "o", "o"),
                new ArrayList<>(),
                new HashSet<>());

        Integer film1 = filmDbStorage.addFilm(film_1);
        Integer film2 = filmDbStorage.addFilm(film_2);

        Collection<Film> beforeDelete = filmDbStorage.getAllFilms();
        filmDbStorage.deleteFilm(film1);
        Collection<Film> afterDelete = filmDbStorage.getAllFilms();
        assertEquals(beforeDelete.size() - 1, afterDelete.size());
        filmDbStorage.deleteFilm(film2);
    }


    @Test
    void shouldAddLike() {
        Film film_1 = new Film(1, "Film_1", "Description_1",
                LocalDate.now().minusYears(8),
                90,
                3,
                new Mpa(1, "Name", "Description"),
                new ArrayList<>(),
                new HashSet<>());

        Integer film1 = filmDbStorage.addFilm(film_1);

        User user_1 = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());

        int userId = userDbStorage.addUser(user_1);

        filmDbStorage.addLike(film1, userId);
        assertEquals(1, filmDbStorage.getFilmById(film1).getLikesCount().size());

        filmDbStorage.deleteFilm(film1);
        userDbStorage.deleteUserById(userId);
    }
}
