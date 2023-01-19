package ru.yandex.practicum.filmorate.model;

import lombok.ToString;
import ru.yandex.practicum.filmorate.annotations.CorrectReleaseDate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString
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

    private Set<Integer> likesCount;

    public Film(String name, String description, LocalDate releaseDate, int duration, Set<Integer> likesCount) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likesCount = Objects.requireNonNullElseGet(likesCount, HashSet::new);
    }

    public void addLike(Integer userId) {
        if (likesCount == null) {
            likesCount = new HashSet<>();
        }
        likesCount.add(userId);
    }

    public void removeLike(Integer userId) {
        likesCount.remove(userId);
    }
}
