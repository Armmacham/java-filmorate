package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.CorrectLogin;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class User {

    @PositiveOrZero(message = "id can not be negative")
    private int id;

    @NotBlank(message = "email must not be null")
    @Email(message = "incorrect email format")
    private String email;

    @NotBlank(message = "login must not be empty")
    @CorrectLogin
    private String login;

    @NotEmpty(message = "name must not be empty")
    @NotBlank(message = "name must not be blank")
    private String name;

    @Past(message = "birthday can not be in present or future")
    private LocalDate birthday;
}
