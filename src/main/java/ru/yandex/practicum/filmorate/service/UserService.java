package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    private final Validator validator;

    @Autowired
    public UserService(Validator validator, @Qualifier("UserDbStorage") UserStorage userStorage) {
        this.validator = validator;
        this.userStorage = userStorage;
    }

    public User addUser(final User user) {
        if (validator.validate(user).size() > 0) {
            throw new ValidationException();
        }
        setUserNameByLogin(user, "Добавлен пользователь");
        int id = userStorage.addUser(user);
        return userStorage.getUserById(id);
    }

    public User updateUser(final User user) {
        setUserNameByLogin(user, "Обновлён пользователь");
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void setUserNameByLogin(User user, String text) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("{}: {}, email: {}", text, user.getName(), user.getEmail());
    }

    public User getUserById(final Integer userId) {
        return userStorage.getUserById(userId);
    }

    public void addFriend(final Integer userId, final Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        userStorage.addFriend(userId, friendId);
        log.debug("Пользователь с id {} добавил в список друзей пользователя с id {}", userId, friendId);
    }

    public void removeFriend(final Integer userId, final Integer friendId) {
        User user = getUserById(userId);
        user.removeFriend(friendId);
        userStorage.deleteFriend(userId, friendId);
        log.debug("Пользователь с id {} удалил из списка друзей пользователя с id {}", userId, friendId);
    }

    public List<User> getUserFriends(final Integer userId) {
        return userStorage.getUserFriends(userId);
    }

    public List<User> getMutualFriends(final Integer firstUserId, final Integer secondUserId) {
        List<Integer> otherFriendsId = getUserById(secondUserId).getAllFriendsId();

        return getUserById(firstUserId).getAllFriendsId()
                .stream()
                .filter(otherFriendsId::contains)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public boolean deleteUser(final User user) {
        return userStorage.deleteUserById(user.getId());
    }
}
