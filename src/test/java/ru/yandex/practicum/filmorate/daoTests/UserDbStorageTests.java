package ru.yandex.practicum.filmorate.daoTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTests {
    private final UserDbStorage userDbStorage;

    @Test
    public void shouldGetUserById() {
        User user = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        int addUser = userDbStorage.addUser(user);
        User dbUser = userDbStorage.getUserById(addUser);
        assertThat(dbUser).hasFieldOrPropertyWithValue("id", addUser);
        userDbStorage.deleteUserById(addUser);
    }

    @Test
    void shouldGetAllUsers() {
        User user1 = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        User user2 = new User(2,
                "correct.email2@mail.ru",
                "correct_login2",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        int addUser1 = userDbStorage.addUser(user1);
        int addUser2 = userDbStorage.addUser(user2);
        Collection<User> dbUsers = userDbStorage.getAllUsers();
        assertEquals(2, dbUsers.size());
        userDbStorage.deleteUserById(addUser1);
        userDbStorage.deleteUserById(addUser2);
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        int addedUser = userDbStorage.addUser(user);
        User userById = userDbStorage.getUserById(addedUser);
        userById.setName("Ivan");
        userDbStorage.updateUser(userById);
        User dbUser = userDbStorage.getUserById(addedUser);
        assertThat(dbUser).hasFieldOrPropertyWithValue("name", "Ivan");
        userDbStorage.deleteUserById(addedUser);
    }

    @Test
    void shouldDeleteUser() {
        User user1 = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        User user2 = new User(2,
                "correct.email2@mail.ru",
                "correct_login2",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        int addedUser1 = userDbStorage.addUser(user1);
        int addedUser2 = userDbStorage.addUser(user2);
        Collection<User> beforeDelete = userDbStorage.getAllUsers();
        userDbStorage.deleteUserById(addedUser1);
        Collection<User> afterDelete = userDbStorage.getAllUsers();
        assertEquals(beforeDelete.size() - 1, afterDelete.size());
        userDbStorage.deleteUserById(addedUser2);
    }
}
