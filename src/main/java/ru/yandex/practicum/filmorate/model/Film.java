package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.MinDataReleaseFilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
    private Set<Long> like;
    private int rate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return duration == film.duration && rate == film.rate && id.equals(film.id) && name.equals(film.name) && description.equals(film.description) && releaseDate.equals(film.releaseDate) && like.equals(film.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration, like, rate);
    }
}
