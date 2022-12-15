package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.yandex.practicum.filmorate.annotations.CorrectLogin;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@ToString
public class User {

    @PositiveOrZero(message = "id can not be negative")
    private int id;

    @NotNull(message = "email must not be null")
    @Email(message = "incorrect email format")
    private String email;

    @NotBlank(message = "login must not be blank")
    @CorrectLogin
    private String login;

    private String name;

    @Past(message = "birthday can not be in present or future")
    private LocalDate birthday;
}
