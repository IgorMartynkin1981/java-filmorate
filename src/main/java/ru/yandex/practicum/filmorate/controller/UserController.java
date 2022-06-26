package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту на добавление пользователя: " + user);
        if (findEmail(user)) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        isEmptyNameUser(user);
        findMaxId();
        user.setId(id);
        users.put(user.getId(), user);
        log.info("Пользователь '{}' добавлен", user);
        id++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту на изменение данных пользователя: '{}'", user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Изменения успешно внесены");
        } else {
            createUser(user);
            log.info("Пользователь не существует: '{}'", user);
        }
        return user;
    }

    private void findMaxId() {
        if (!users.isEmpty()) {
            for (User userIds : users.values()) {
                if (userIds.getId() >= id) {
                    id = userIds.getId() + 1;
                }
            }
        }
    }

    private boolean findEmail(User user) {
        for (User o : users.values()) {
            if (o.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private void isEmptyNameUser(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
