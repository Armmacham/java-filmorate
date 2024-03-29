package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.filmorate.annotations.CorrectLogin;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
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

    private List<Integer> friends;

    public List<Integer> checkFriendsList() {
        if (friends != null) {
            return friends;
        }
        friends = new ArrayList<>();
        return friends;
    }

    public void addFriend(Integer id) {
        checkFriendsList();
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        checkFriendsList();
        if (!friends.contains(id)) {
            throw new RuntimeException("Такого друга не найдено");
        }
        friends.remove(id);
    }

    public List<Integer> getAllFriendsId() {
        checkFriendsList();
        return friends;
    }
}
