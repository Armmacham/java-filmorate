package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        setUserNameByLogin(user, "Добавлен пользователь");
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
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

    public User getUserById(Integer userId) {
        return userStorage.getUserById(userId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.debug("Пользователь с id {} добавил в список друзей пользователя с id {}", userId, friendId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.debug("Пользователь с id {} удалил из списка друзей пользователя с id {}", userId, friendId);
    }

    public List<User> getUserFriends(Integer userId) {
        return userStorage.getUserFriends(userId);
    }

    public Set<User> getMutualFriends(Integer firstUserId, Integer secondUserId) {
        return getUserById(firstUserId).getAllFriendsId()
                .stream()
                .filter(getUserById(secondUserId).getAllFriendsId()::contains)
                .map(this::getUserById)
                .collect(Collectors.toSet());
    }
}
