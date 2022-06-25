package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final HashMap<String, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        //checkEmail(user);
        log.info("Получен запрос к эндпоинту на добавление пользователя: " + user);
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        findMaxId();
        user.setId(id);
        users.put(user.getEmail(), user);
        log.info("Пользователь '{}' добавлен", user);
        id++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        //checkEmail(user);
        log.info("Получен запрос к эндпоинту на изменение данных пользователя: '{}'", user);
        users.put(user.getEmail(), user);
        log.info("Изменения успешно внесены");
        return user;
    }

    /*
    private void checkEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
    }
    */

    private void findMaxId() {
        if (!users.isEmpty()) {
            for (User userIds : users.values()) {
                if (userIds.getId() >= id) {
                    id = userIds.getId() + 1;
                }
            }
        }
    }
}
