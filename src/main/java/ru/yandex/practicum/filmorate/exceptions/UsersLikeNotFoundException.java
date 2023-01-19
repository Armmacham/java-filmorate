package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsersLikeNotFoundException extends RuntimeException {

    public UsersLikeNotFoundException(String message) {
        super(message);
    }
}
