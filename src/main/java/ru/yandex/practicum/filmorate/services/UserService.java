package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
@Component
@Slf4j
@AllArgsConstructor
public class UserService {

    private final Map<Integer, User> users = new HashMap<>();

    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
