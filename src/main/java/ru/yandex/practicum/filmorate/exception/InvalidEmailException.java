package ru.yandex.practicum.filmorate.exception;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message, @NotNull @NotBlank(message = "Адрес электронной почты не может быть пустым.") @Email(message = "Email должен быть корректным адресом электронной почты") String email) {
        super(message);
    }
}