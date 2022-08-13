package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.MinDataReleaseFilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class Film {
    @PositiveOrZero(message = "Id должен быть положительным")
    private Long id;
    @NotNull
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание фильма не может быть больше 200 символов.")
    private String description;
    @MinDataReleaseFilmValidation
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private RatingMPA mpa;
    //private Collection<Genre> genres;
}
