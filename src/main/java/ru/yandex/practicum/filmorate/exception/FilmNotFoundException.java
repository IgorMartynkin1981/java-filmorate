package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String message){
        super(message);
    }
}
