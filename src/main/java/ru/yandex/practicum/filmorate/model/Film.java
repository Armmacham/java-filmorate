package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.annotations.CorrectReleaseDate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
public class Film {

    @PositiveOrZero(message = "id cant not be negative")
    private int id;

    @NotBlank(message = "name can not be blank")
    private String name;

    @Length(message = "description can not be more then 200")
    private String description;

    @CorrectReleaseDate(message = "releaseDate must be after 28-DEC-1895")
    private LocalDate releaseDate;

    @PositiveOrZero(message = "duration can not be negative")
    private int duration;

    /*public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }*/

}
