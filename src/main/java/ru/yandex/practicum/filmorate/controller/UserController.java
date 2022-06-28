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
        findEmailUser(user);
        createNameUserIsEmpty(user);
        findMaxIdUsers();
        user.setId(id);
        users.put(user.getId(), user);
        log.info("Пользователь '{}' добавлен", user);
        id++;
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws Exception {
        log.info("Получен запрос к эндпоинту на изменение данных пользователя: '{}'", user);
        if (user.getId() < 0) {
            log.warn("id пользователя {} не может быть меньше 0", user);
            throw new Exception();
        }
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Изменения успешно внесены");
        } else {
            createUser(user);
        }
        return user;
    }

    private void findMaxIdUsers() {
        if (!users.isEmpty()) {
            for (User userIds : users.values()) {
                if (userIds.getId() >= id) {
                    id = userIds.getId() + 1;
                }
            }
        }
    }

    private void findEmailUser(User user) {
        for (User o : users.values()) {
            if (o.getEmail().equals(user.getEmail())) {
                log.warn("Пользователь с электронной почтой {} уже зарегистрирован.", user.getEmail());
                throw new UserAlreadyExistException();
            }
        }
    }

    private void createNameUserIsEmpty(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
