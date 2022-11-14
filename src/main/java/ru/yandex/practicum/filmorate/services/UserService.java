package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
@Getter
@Service
@Repository
public class UserService {
    private final UserStorage userStorage;

    public Map<Integer, User> getAllUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public void setUserNameByLogin(User user, String text) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("{} пользователь: {}, email: {}", text, user.getName(), user.getEmail());
    }
}
