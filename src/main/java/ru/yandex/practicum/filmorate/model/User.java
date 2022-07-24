package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class User {
    @PositiveOrZero(message = "Id должен быть положительным")
    private Long id;
    @NotNull
    @NotBlank(message = "Адрес электронной почты не может быть пустым.")
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Login может состоять из букв и цифр")
    @NotBlank(message = "Необходимо указать логин")
    private String login;
    private String name;
    @Past(message = "Дата рождения не должна быть больше текущей")
    private LocalDate birthday;
    private Set<Long> friends;
}
