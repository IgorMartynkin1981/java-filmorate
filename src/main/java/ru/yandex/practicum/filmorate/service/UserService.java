package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage.FriendDAO;
import ru.yandex.practicum.filmorate.dao.UserDbStorage.UserDAO;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final FriendDAO friendDAO;

    public Collection<User> findAll() {
        return userDAO.findAll();
    }

    public User findUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение id пользователя не может быть меньше 0");
        }
        return userDAO.findUser(id);
    }

    public User createUser(User user) {
        createNameUserIsEmpty(user);
        return userDAO.createUser(user);
    }
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
