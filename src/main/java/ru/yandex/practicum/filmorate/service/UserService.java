package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findUser(Integer id) {
        return userStorage.findUser(id);
    }

    public User createUser(User user) {
        log.info("Получен запрос к эндпоинту на добавление пользователя {}", user);
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

    public User createFriends(Integer userId, Integer friendId) {
        log.info("Получен запрос к эндпоинту на добавление пользователю {} друга {}",
                userStorage.findUser(userId),
                userStorage.findUser(friendId));
        Set<Integer> friendsId = new TreeSet<>();
        User user = userStorage.findUser(userId);
        if (user.getFriends() != null) {
            friendsId.addAll(user.getFriends());
        }
        friendsId.add(friendId);
        user.setFriends(friendsId);
        return updateUser(user);
    }

    public User deleteFriends(Integer userId, Integer friendId) {
        log.info("Получен запрос к эндпоинту на удаление у пользователя {} друга {}",
                userStorage.findUser(userId),
                userStorage.findUser(friendId));
        Set<Integer> friendsId = new TreeSet<>();
        User user = userStorage.findUser(userId);
        if (user.getFriends() != null) {
            friendsId.addAll(user.getFriends());
        } else {
            log.info("У пользователя {} нет друзей", userStorage.findUser(userId));
        }
        friendsId.remove(friendId);
        user.setFriends(friendsId);
        return updateUser(user);
    }

    public List<User> findFriendsUser(Integer id) {
        List<User> friendsUser = new ArrayList<>();
        if (userStorage.findUser(id).getFriends() != null) {
            for (Integer i : userStorage.findUser(id).getFriends()) {
                friendsUser.add(findUser(i));
            }
        } else {
            log.info("У пользователя {} нет друзей", userStorage.findUser(id));
        }
        return friendsUser;
    }

    public List<User> findCommonsFriend(Integer idFirst, Integer idSecond) {
        List<User> friendsUser = new ArrayList<>();
        List<Integer> idsCommon = new ArrayList<>(findUser(idFirst).getFriends());
        idsCommon.retainAll(findUser(idSecond).getFriends());
        if (findUser(idFirst).getFriends() != null || findUser(idSecond).getFriends() != null) {
            for (Integer i : idsCommon) {
                friendsUser.add(findUser(i));
            }
        } else {
            log.info("У пользователя нет общих друзей");
        }
        return friendsUser;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final RuntimeException e) {
        return new ErrorResponse(
                "Ошибка с параметром count.", e.getMessage()
        );
    }
}
