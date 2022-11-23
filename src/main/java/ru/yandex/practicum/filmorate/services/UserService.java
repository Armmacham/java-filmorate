package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        if (userStorage.getUsers().containsKey(user.getId())) {
            throw new RuntimeException("Такой пользователь уже существует");
        }
        setUserNameByLogin(user, "Добавлен пользователь");
        return userStorage.add(user);
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>(userStorage.getUsers().values());
        return usersList;
    }

    public User updateUser(User user) {
        if (!userStorage.getUsers().containsKey(user.getId())) {
            throw new RuntimeException("Такого пользователя не существует");
        }
        setUserNameByLogin(user, "Обновлены данные пользователя");
        return userStorage.update(user);
    }

    public void setUserNameByLogin(User user, String text) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("{}: {}, email: {}", text, user.getName(), user.getEmail());
    }

}
