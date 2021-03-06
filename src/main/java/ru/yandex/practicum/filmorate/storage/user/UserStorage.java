package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> findAll();

    User findUser(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUsers();

    void deleteUser(User user);
}
