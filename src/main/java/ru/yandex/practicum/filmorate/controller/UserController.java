package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.TreeMap;

@RestController
@RequestMapping("/users")
public class UserController {
    private final TreeMap<String, User> users = new TreeMap<>((o1, o2) -> {
        if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        }
        return Integer.compare(o1.compareTo(o2), 0);
    });

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!users.isEmpty()) {
            user.setId(users.get(users.lastKey()).getId());
        } else {
            user.setId(1);
        }
        users.put(user.getEmail(), user);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        users.put(user.getEmail(), user);

        return user;
    }
}
