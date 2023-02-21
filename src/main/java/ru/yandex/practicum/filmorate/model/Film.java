package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotations.CorrectReleaseDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Data
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @PositiveOrZero(message = "id cant not be negative")
    private int id;

    @NotBlank(message = "name can not be blank")
    private String name;

    @Length(min = 1, max = 200, message = "description can not be more then 200")
    private String description;

    @CorrectReleaseDate(message = "releaseDate must be after 28-DEC-1895")
    private LocalDate releaseDate;

    @PositiveOrZero(message = "duration can not be negative")
    private int duration;

    @NotNull
    private int rating;

    private Mpa mpa;

    private List<Genre> genres = new ArrayList<>();

    private List<Integer> likesCount = new ArrayList<>();

        /*public Film(int filmId, String name, String description, LocalDate releaseDate, int duration, int rating, Mpa mpa, List<Genre> filmGenres, List<Integer> filmLikes) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likesCount = Objects.requireNonNullElseGet(likesCount, HashSet::new);
    }*/

    /*public Film(String name, String description, LocalDate releaseDate, int duration, int rate, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likesCount = Objects.requireNonNullElseGet(likesCount, HashSet::new);
    }*/

    public void addLike(Integer userId) {
        likesCount.add(userId);
    }

    public void removeLike(Integer userId) {
        likesCount.remove(userId);
    }
}
