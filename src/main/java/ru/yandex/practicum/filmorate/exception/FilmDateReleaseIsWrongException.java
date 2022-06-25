package ru.yandex.practicum.filmorate.exception;

public class FilmDateReleaseIsWrongException extends RuntimeException{
    public FilmDateReleaseIsWrongException(String message) {
        super(message);
    }
}
