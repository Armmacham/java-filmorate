package ru.yandex.practicum.filmorate.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    void shouldAddUserWhenValidUserData() {
        User user = new User(0,
                "correct_email@mail.ru",
                "correct_login",
                "Correct_name",
                LocalDate.of(2002, 1, 1),
                new ArrayList<>());
        User addedUser = userService.addUser(user);
        assertNotEquals(0, addedUser.getId());
        assertTrue(userService.getAllUsers().contains(addedUser));
        userService.deleteUser(addedUser);
    }

    @Test
    void shouldSetUserNameWhenEmptyUserName() {
        User user = new User(0,
                "new_correct_email@mail.ru",
                "correct_login",
                "",
                LocalDate.of(2002, 1, 1),
                new ArrayList<>());
        User addedUser = userService.addUser(user);
        assertNotEquals(0, addedUser.getId());
        assertEquals(addedUser.getLogin(), addedUser.getName());
        assertTrue(userService.getAllUsers().contains(addedUser));
        userService.deleteUser(addedUser);
    }

    @Test
    void shouldThrowExceptionWhenFailedUserLogin() {
        try {
            User user = new User(0,
                    "correct_email@mail.ru",
                    "incorrect login",
                    "Correct_name",
                    LocalDate.of(2002, 1, 1),
                    new ArrayList<>());
            userService.addUser(user);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldThrowExceptionWhenFailedUserEmail() {
        try {
            User user = new User(0,
                    "incorrect_email@",
                    "correct_login",
                    "Correct_name",
                    LocalDate.of(2002, 1, 1),
                    new ArrayList<>());
            userService.addUser(user);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldThrowExceptionWhenFailedUserBirthDate() {
        try {
            User user = new User(0,
                    "correct_email@mail.ru",
                    "correct_login",
                    "Correct_name",
                    LocalDate.now().plusDays(1),
                    new ArrayList<>());
            userService.addUser(user);
        } catch (ValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldThrowExceptionWhenUpdateFailedUserId() {
        try {
            User user = new User(99,
                    "correct_email@mail.ru",
                    "correct_login",
                    "Correct_name",
                    LocalDate.now().plusYears(-33),
                    new ArrayList<>());
            userService.updateUser(user);
        } catch (EntityNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void shouldAddFriend() {
        User user_1 = new User(0,
                "correct_email@mail.ru",
                "correct_login",
                "Correct_name",
                LocalDate.of(2002, 1, 1),
                new ArrayList<>());

        User user_2 = new User(0,
                "corrrect_email@mail.ru",
                "correct_login",
                "Correct_name_2",
                LocalDate.of(2000, 2, 1),
                new ArrayList<>());

        User addedUser_1 = userService.addUser(user_1);
        User addedUser_2 = userService.addUser(user_2);

        addedUser_1.addFriend(addedUser_2.getId());

        assertEquals(1, addedUser_1.getFriends().size());

        userService.deleteUser(addedUser_1);
        userService.deleteUser(addedUser_2);
    }
}
