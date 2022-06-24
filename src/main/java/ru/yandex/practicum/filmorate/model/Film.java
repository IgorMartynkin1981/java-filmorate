package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private double duration;
}
