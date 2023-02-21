package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    @Test
    void contextLoads() {
    }

    /*// Тесты для FilmStorage
    @Test
    void shouldAddLike() {
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, null);
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        inMemoryFilmStorage.addFilm(film);
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), null);
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        inMemoryUserStorage.addUser(user);
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
        filmService.addLike(film.getId(), user.getId());
        assertEquals(1, film.getLikesCount().size());
    }

    @Test
    void shouldGenerateNextFilmId() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        int id = inMemoryFilmStorage.getId();
        inMemoryFilmStorage.generateId();
        assertEquals((id + 1), inMemoryFilmStorage.getId());
    }

    @Test
    void shouldAddFilm() {
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        inMemoryFilmStorage.addFilm(film);
        assertNotNull(inMemoryFilmStorage.getAllFilms());
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        inMemoryFilmStorage.addFilm(film);
        Film changedFilm = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 120, Set.of(1, 2));
        changedFilm.setId(film.getId());
        inMemoryFilmStorage.update(changedFilm);
        assertEquals(changedFilm.getDuration(), inMemoryFilmStorage.getFilmById(film.getId()).getDuration());
    }

    // Тесты для UserStorage
    @Test
    void shouldGenerateNextUserId() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        int id = inMemoryUserStorage.getId();
        inMemoryUserStorage.generateId();
        assertEquals((id + 1), inMemoryUserStorage.getId());
    }

    @Test
    void shouldAddUser() {
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), Set.of(1, 2));
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        inMemoryUserStorage.addUser(user);
        assertNotNull(inMemoryUserStorage.getUsers());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), Set.of(1, 2));
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        inMemoryUserStorage.addUser(user);
        User FixedUser = new User(user.getId(), "mail@mail.com", "User_X", "Max", LocalDate.of(1991, 11, 1), Set.of(1, 2));
        inMemoryUserStorage.updateUser(FixedUser);
        Map<Integer, User> users = inMemoryUserStorage.getUsers();
        assertEquals(FixedUser.getLogin(), users.get(user.getId()).getLogin());
    }

    // Тесты для FilmService
    @Test
    void shouldNotAddFilmIfIdAlreadyExist() {
        try {
            InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
            InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
            Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
            FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
            filmService.addFilm(film);
            Film film_2 = new Film("Film_2", "Description_2", LocalDate.of(2002, 11, 1), 60, Set.of(1));
            film_2.setId(film.getId());
            filmService.addFilm(film_2);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldAddFilmService() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
        int initialListSize = filmService.getAllFilms().size();
        filmService.addFilm(film);
        assertEquals(initialListSize + 1, filmService.getAllFilms().size());
    }

    @Test
    public void shouldGetAllFilms() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
        Film film_2 = new Film("Film_2", "Description_2", LocalDate.of(2002, 11, 1), 60, Set.of(1));
        filmService.addFilm(film);
        filmService.addFilm(film_2);
        assertEquals(List.of(film, film_2), filmService.getAllFilms());
    }

    @Test
    public void shouldNotUpdateNotExistingFilm() {
        try {
            InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
            InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
            FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
            Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
            filmService.updateFilm(film);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldUpdateFilmService() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
        Film film = new Film("Film_1", "Description", LocalDate.of(1991, 11, 1), 90, Set.of(1, 2));
        filmService.addFilm(film);
        film.setDuration(100);
        filmService.updateFilm(film);
        assertEquals(film.toString(), filmService.getAllFilms().get(0).toString());
    }

    @Test
    public void shouldNotValidateReleaseDate() {
        try {
            InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
            InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
            FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
            Film film = new Film("Film_1", "Description", LocalDate.of(1000, 11, 1), 90, List.of(1, 2));
            filmService.validateReleaseDate(film, "Text");
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    //Тесты для UserService
    @Test
    public void shouldNotAddUserIfIdAlreadyExist() {
        try {
            InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
            User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
            UserService userService = new UserService(inMemoryUserStorage);
            userService.addUser(user);
            User user_2 = new User(user.getId(), "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1));
            userService.addUser(user_2);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldAddUserService() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
        UserService userService = new UserService(inMemoryUserStorage);
        userService.addUser(user);
        assertNotNull(userService.getAllUsers());
    }

    @Test
    public void shouldGetAllUsers() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
        User user_2 = new User(0, "mail_2@mail.com", "User_2", "Max_2", LocalDate.of(1990, 10, 10), List.of(1));
        UserService userService = new UserService(inMemoryUserStorage);
        userService.addUser(user);
        userService.addUser(user_2);
        assertEquals(List.of(user, user_2), userService.getAllUsers());
    }

    @Test
    public void shouldUpdateUserService() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
        UserService userService = new UserService(inMemoryUserStorage);
        userService.addUser(user);
        user.setName("Max_2");
        userService.updateUser(user);
        assertEquals(user.toString(), userService.getAllUsers().get(0).toString());
    }

    @Test
    public void shouldNotUpdateUserIfNotExist() {
        try {
            InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
            User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
            UserService userService = new UserService(inMemoryUserStorage);
            user.setName("Max_2");
            userService.updateUser(user);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldSetUserNameByLogin() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User(0, "mail@mail.com", "User_1", null, LocalDate.of(1991, 11, 1), List.of(1, 2));
        UserService userService = new UserService(inMemoryUserStorage);
        userService.addUser(user);
        assertEquals("User_1", userService.getAllUsers().get(0).getName());
    }

    @Test
    public void shouldNotSetUserNameByLogin() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User(0, "mail@mail.com", "User_1", "Max", LocalDate.of(1991, 11, 1), List.of(1, 2));
        UserService userService = new UserService(inMemoryUserStorage);
        userService.addUser(user);
        assertEquals("Max", userService.getAllUsers().get(0).getName());
    }

    //Тесты для*/
}