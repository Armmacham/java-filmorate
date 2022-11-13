package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>(userService.getUsers().values());
        log.debug("Количество пользователей: {}", usersList.size());
        return usersList;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("User added");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("User updated");
        return userService.updateUser(user);
    }
}
