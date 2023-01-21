package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
public class InMemoryUserStorage implements UserStorage {
    public Map<Integer, User> users = new HashMap<>();

    public static int id;

    public int generateId() {
        return ++id;
    }

    @Override
    public User addUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new EntityAlreadyExistsException(String.format("Пользователь с id номером %d уже существует", user.getId()));
        }
        int newId = generateId();
        user.setId(newId);
        users.put(newId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new EntityNotFoundException(String.format("Пользователь с id номером %d не найден", user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    public int getId() {
        return id;
    }

    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new EntityNotFoundException(String.format("Пользователь с id номером %d не найден", id));
        }
        return users.get(id);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>(users.values());
        return allUsers;
    }
}