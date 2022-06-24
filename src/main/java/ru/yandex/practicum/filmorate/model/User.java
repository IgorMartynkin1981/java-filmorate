package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "must not contain special characters")
    @NotBlank(message = "Необходимо указать логин")
    private String login;
    private String name;
    @Past(message = "Дата рождения не должна быть больше текущей")
    private LocalDate birthday;
}
