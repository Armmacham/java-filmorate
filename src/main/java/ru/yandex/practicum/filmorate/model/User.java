package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;

@Data
public class User {

    @PositiveOrZero(message = "id can not be negative")
    private int id;

    @NotBlank(message = "email must not be null")
    @Email(message = "email must not be null")
    private String email;

    @NotBlank(message = "login must not be empty")
    private String login;

    @NotBlank(message = "name must not be empty")
    private String name;

    @Past(message = "birthday can not be in present or future")
    private Instant birthday;

    /*public User(int id, String email, String login, String name, Instant birthday) {

    }*/
}
