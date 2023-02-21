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
        User addUser = userDbStorage.addUser(user);
        User dbUser = userDbStorage.getUserById(addUser.getId());
        assertThat(dbUser).hasFieldOrPropertyWithValue("id", addUser.getId());
        userDbStorage.deleteUser(addUser);
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
        User addUser1 = userDbStorage.addUser(user1);
        User addUser2 = userDbStorage.addUser(user2);
        Collection<User> dbUsers = userDbStorage.getAllUsers();
        assertEquals(2, dbUsers.size());
        userDbStorage.deleteUser(addUser1);
        userDbStorage.deleteUser(addUser2);
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(1,
                "correct.email@mail.ru",
                "correct_login",
                "Correct Name",
                LocalDate.of(2016, 1, 1),
                new ArrayList<>());
        User addedUser = userDbStorage.addUser(user);
        addedUser.setName("Ivan");
        userDbStorage.updateUser(addedUser);
        User dbUser = userDbStorage.getUserById(addedUser.getId());
        assertThat(dbUser).hasFieldOrPropertyWithValue("name", "Ivan");
        userDbStorage.deleteUser(addedUser);
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
        User addedUser1 = userDbStorage.addUser(user1);
        User addedUser2 = userDbStorage.addUser(user2);
        Collection<User> beforeDelete = userDbStorage.getAllUsers();
        userDbStorage.deleteUser(addedUser1);
        Collection<User> afterDelete = userDbStorage.getAllUsers();
        assertEquals(beforeDelete.size() - 1, afterDelete.size());
        userDbStorage.deleteUser(addedUser2);
    }
}
