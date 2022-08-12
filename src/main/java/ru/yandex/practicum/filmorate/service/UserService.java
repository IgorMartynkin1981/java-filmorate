package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage.FriendDAO;
import ru.yandex.practicum.filmorate.dao.UserDbStorage.UserDAO;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final UserDAO userDAO;
    private final FriendDAO friendDAO;

    //Перевел в UserDAO
    public Collection<User> findAll() {
        return userDAO.findAll();
    }

    //Перевел в UserDAO
    public User findUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение id пользователя не может быть меньше 0");
        }
        return userDAO.findUser(id);
    }

    //Перевел в UserDAO
    public User createUser(User user) {
        findEmailUser(user);
        createNameUserIsEmpty(user);
        return userDAO.createUser(user);

    }

    //Перевел в UserDAO
    public User updateUser(User user) {
        if (user.getId() <= 0) {
            throw new UserNotFoundException("Значение id пользователя не может быть меньше 0");
        }
        if (findAll().stream().anyMatch(p -> p.getId().equals(user.getId()))) {
            return userDAO.updateUser(user);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public void addFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение id пользователя или друга не может быть меньше 0");
        }
        friendDAO.addFriend(userId, friendId);
    }

    public void deleteFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение id пользователя или друга не может быть меньше 0");
        }
        friendDAO.deleteFriend(userId, friendId);
    }

    public Collection<User> findFriendsUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение id пользователя не может быть меньше 0");
        }
        return friendDAO.findFriendsUser(id);
    }

    public Collection<User> findCommonsFriend(Long idFirst, Long idSecond) {
        if (idFirst <= 0 || idSecond <= 0) {
            throw new UserNotFoundException("Значение id пользователей не может быть меньше 0");
        }
        return friendDAO.findCommonsFriend(idFirst, idSecond);
    }

    private void findEmailUser(User user) {
        for (User o : userDAO.findAll()) {
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

    public void deleteUser(Long id) {
        userDAO.deleteUser(findUser(id));
    }

    public void deleteUsers() {
        userDAO.deleteUsers();
    }
}
