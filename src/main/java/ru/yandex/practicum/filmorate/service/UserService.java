package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findUser(Integer id) {
        return userStorage.findUser(id);
    }

    public User createUser(User user) {
        findEmailUser(user);
        createNameUserIsEmpty(user);
        user.setId(findMaxIdUsers());
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.findAll().contains(user)) {
            return userStorage.updateUser(user);
        } else {
            createUser(user);
        }
        return user;
    }

    private int findMaxIdUsers() {
        int id = 1;
        if (!userStorage.findAll().isEmpty()) {
            for (User userIds : userStorage.findAll()) {
                if (userIds.getId() >= id) {
                    id = userIds.getId() + 1;
                }
            }
        }
        return id;
    }

    private void findEmailUser(User user) {
        for (User o : userStorage.findAll()) {
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
