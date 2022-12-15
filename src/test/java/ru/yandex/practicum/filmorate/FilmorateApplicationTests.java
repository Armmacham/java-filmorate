package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmorateApplicationTests {

    @Test
    void contextLoads() {
    }

    // Тесты для FilmStorage
    @Test
    void shouldGenerateNextFilmId() {
        FilmStorage filmStorage = new FilmStorage();
        int id = filmStorage.getId();
        filmStorage.generateId();
        assertEquals((id + 1), filmStorage.getId());
    }

    @Test
    void shouldAddFilm() {
        Film film = new Film(0, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
        FilmStorage filmStorage = new FilmStorage();
        filmStorage.add(film);
        assertNotNull(filmStorage.getFilms());
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film(0, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
        FilmStorage filmStorage = new FilmStorage();
        filmStorage.add(film);
        Film ChangedFilm = new Film(film.getId(), "Film_1", "Description", LocalDate.of(1991, 11, 1), 120);
        filmStorage.update(ChangedFilm);
        Map<Integer, Film> films = filmStorage.getFilms();
        assertEquals(ChangedFilm.getDuration(), films.get(film.getId()).getDuration());
    }

    // Тесты для UserStorage
    @Test
    void shouldGenerateNextUserId() {
        UserStorage userStorage = new UserStorage();
        int id = userStorage.getId();
        userStorage.generateId();
        assertEquals((id + 1), userStorage.getId());
    }

    @Test
    void shouldAddUser() {
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        UserStorage userStorage = new UserStorage();
        userStorage.add(user);
        assertNotNull(userStorage.getUsers());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        UserStorage userStorage = new UserStorage();
        userStorage.add(user);
        User FixedUser = new User(user.getId(), "mail@mail.com", "User_X", "Max", LocalDate.of(1991, 11, 1));
        userStorage.update(FixedUser);
        Map<Integer, User> users = userStorage.getUsers();
        assertEquals(FixedUser.getLogin(), users.get(user.getId()).getLogin());
    }

    // Тесты для FilmService
    @Test
    void shouldNotAddFilmIfIdAlreadyExist() {
        try {
            FilmStorage filmStorage = new FilmStorage();
            Film film = new Film(0, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
            FilmService filmService = new FilmService(filmStorage);
            filmService.addFilm(film);
            Film film_2 = new Film(film.getId(), "Film_2", "Description_2", LocalDate.of(2002, 11, 1), 60);
            filmService.addFilm(film_2);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldAddFilmService() {
        FilmStorage filmStorage = new FilmStorage();
        Film film = new Film(0, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
        FilmService filmService = new FilmService(filmStorage);
        int initialListSize = filmService.getAllFilms().size();
        filmService.addFilm(film);
        assertEquals(initialListSize + 1, filmService.getAllFilms().size());
    }

    @Test
    public void shouldGetAllFilms() {
        FilmStorage filmStorage = new FilmStorage();
        FilmService filmService = new FilmService(filmStorage);
        Film film = new Film(0, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
        Film film_2 = new Film(0, "Film_2", "Description_2", LocalDate.of(2002, 11, 1), 60);
        filmService.addFilm(film);
        filmService.addFilm(film_2);
        assertEquals(List.of(film, film_2), filmService.getAllFilms());
    }

    @Test
    public void shouldNotUpdateNotExistingFilm() {
        try {
            FilmStorage filmStorage = new FilmStorage();
            FilmService filmService = new FilmService(filmStorage);
            Film film = new Film(1, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
            filmService.updateFilm(film);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldUpdateFilmService() {
        FilmStorage filmStorage = new FilmStorage();
        FilmService filmService = new FilmService(filmStorage);
        Film film = new Film(1, "Film_1", "Description", LocalDate.of(1991, 11, 1), 90);
        filmService.addFilm(film);
        film.setDuration(100);
        filmService.updateFilm(film);
        assertEquals(film.toString(), filmService.getAllFilms().get(0).toString());
    }

    @Test
    public void shouldNotValidateReleaseDate() {
        try {
            FilmStorage filmStorage = new FilmStorage();
            FilmService filmService = new FilmService(filmStorage);
            Film film = new Film(1, "Film_1", "Description", LocalDate.of(1000, 11, 1), 90);
            filmService.validateReleaseDate(film, "Text");
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    //Тесты для UserService
    @Test
    public void shouldNotAddUserIfIdAlreadyExist() {
        try {
            UserStorage userStorage = new UserStorage();
            User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
            UserService userService = new UserService(userStorage);
            userService.addUser(user);
            User user_2 = new User(user.getId(), "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
            userService.addUser(user_2);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldAddUserService() {
        UserStorage userStorage = new UserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        UserService userService = new UserService(userStorage);
        userService.addUser(user);
        assertNotNull(userService.getAllUsers());
    }

    @Test
    public void shouldGetAllUsers() {
        UserStorage userStorage = new UserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        User user_2 = new User(0, "mail_2@mail.com", "User_2", "Max_2", LocalDate.of(1990, 10, 10));
        UserService userService = new UserService(userStorage);
        userService.addUser(user);
        userService.addUser(user_2);
        assertEquals(List.of(user, user_2), userService.getAllUsers());
    }

    @Test
    public void shouldUpdateUserService() {
        UserStorage userStorage = new UserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        UserService userService = new UserService(userStorage);
        userService.addUser(user);
        user.setName("Max_2");
        userService.updateUser(user);
        assertEquals(user.toString(), userService.getAllUsers().get(0).toString());
    }

    @Test
    public void shouldNotUpdateUserIfNotExist() {
        try {
            UserStorage userStorage = new UserStorage();
            User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
            UserService userService = new UserService(userStorage);
            user.setName("Max_2");
            userService.updateUser(user);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldSetUserNameByLogin() {
        UserStorage userStorage = new UserStorage();
        User user = new User(0, "mail@mail.com", "User_1", null, LocalDate.of(1991, 11, 1));
        UserService userService = new UserService(userStorage);
        userService.addUser(user);
        assertEquals("User_1", userService.getAllUsers().get(0).getName());
    }

    @Test
    public void shouldNotSetUserNameByLogin() {
        UserStorage userStorage = new UserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1));
        UserService userService = new UserService(userStorage);
        userService.addUser(user);
        assertEquals("Max", userService.getAllUsers().get(0).getName());
    }
}