package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    int addUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);

    List<User> getUserFriends(Integer id);

    List<User> getAllUsers();

    boolean deleteUserById(int id);

    boolean addFriend(int userId, int friendId);

    boolean deleteFriend(int userId, int friendId);
}
