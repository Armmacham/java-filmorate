package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class UserStorage {
    public Map<Integer, User> users = new HashMap<>();

    public static int id;

    public int generateId() {
        return ++id;
    }
    public User add(User user) {
        int newId = generateId();
        user.setId(newId);
        users.put(newId, user);
        return user;
    }
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public int getId() {
        return id;
    }
}