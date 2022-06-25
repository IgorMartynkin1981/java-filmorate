package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Film {
    @PositiveOrZero(message = "Id должен быть положительным")
    private int id;
    @NotNull
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание фильма не может быть больше 200 символов.")
    private String description;
    private LocalDate releaseDate;
    @Positive
    private double duration;
}
