package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findUser(Integer idUser) {
        return users.get(idUser);
    }

    @Override
    public User createUser(User user) {
        findEmailUser(user);
        createNameUserIsEmpty(user);
        findMaxIdUsers();
        user.setId(id);
        users.put(user.getId(), user);
        log.info("Пользователь '{}' добавлен", user);
        id++;
        return user;
    }

    @Override
    public User updateUser(User user) {
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
