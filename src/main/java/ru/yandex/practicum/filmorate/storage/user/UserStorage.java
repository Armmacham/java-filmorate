package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean addFriend(int userId, int friendId);

    boolean deleteFriend(int userId, int friendId);
}
