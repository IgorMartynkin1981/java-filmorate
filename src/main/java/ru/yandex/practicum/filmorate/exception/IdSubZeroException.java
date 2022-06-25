package ru.yandex.practicum.filmorate.exception;

public class IdSubZeroException extends RuntimeException{
    public IdSubZeroException(String message) {
        super(message);
    }
}
