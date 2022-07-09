package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users = new HashMap<>();

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
        users.put(user.getId(), user);
        log.info("Пользователь '{}' добавлен", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        log.info("Измененния для пользователя '{}' внесены", user);
        return user;
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId());
    }
}
