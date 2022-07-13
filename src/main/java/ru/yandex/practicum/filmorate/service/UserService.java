package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
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
        if (user.getId() <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
        if (findAll().stream().anyMatch(p -> p.getId().equals(user.getId()))) {
            return userStorage.updateUser(user);
        } else {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
    }

    public User createFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
        log.info("Получен запрос к эндпоинту на добавление пользователю {} друга {}",
                findUser(userId),
                findUser(friendId));
        User user = addFriend(userId, friendId);
        User friend = addFriend(friendId, userId);
        return user;
    }

    private User addFriend(Long userId, Long friendId) {
        Set<Long> friendsId = new TreeSet<>();
        User user = findUser(userId);
        if (user.getFriends() != null) {
            friendsId.addAll(user.getFriends());
        }
        friendsId.add(friendId);
        user.setFriends(friendsId);
        updateUser(user);
        return user;
    }

    public User deleteFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
        log.info("Получен запрос к эндпоинту на удаление у пользователя {} друга {}",
                userStorage.findUser(userId),
                userStorage.findUser(friendId));
        User user = deleteFriend(userId, friendId);
        User friend = deleteFriend(friendId, userId);
        return user;
    }

    private User deleteFriend(Long userId, Long friendId) {
        Set<Long> friendsId = new TreeSet<>();
        User user = userStorage.findUser(userId);
        if (user.getFriends() != null) {
            friendsId.addAll(user.getFriends());
        } else {
            log.info("У пользователя {} нет друзей", userStorage.findUser(userId));
        }
        friendsId.remove(friendId);
        user.setFriends(friendsId);
        updateUser(user);
        return user;
    }

    public Collection<User> findFriendsUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
        List<User> friendsUser = new ArrayList<>();
        if (findUser(id).getFriends() != null) {
            for (Long i : findUser(id).getFriends()) {
                friendsUser.add(findUser(i));
            }
        } else {
            log.info("У пользователя {} нет друзей", userStorage.findUser(id));
            return friendsUser;
        }
        return friendsUser;
    }

    public Collection<User> findCommonsFriend(Long idFirst, Long idSecond) {
        if (idFirst <= 0 || idSecond <= 0) {
            throw new UserNotFoundException("Значение не может быть меньше 0");
        }
        List<User> friendsUser = new ArrayList<>();
        Set<Long> firstUser = findUser(idFirst).getFriends();
        Set<Long> secondUser = findUser(idSecond).getFriends();
        if (findUser(idFirst).getFriends() != null || findUser(idSecond).getFriends() != null) {
            List<Long> common = firstUser.stream().filter(secondUser::contains).collect(Collectors.toList());
            for (Long i : common) {
                friendsUser.add(findUser(i));
            }
        } else {
            log.info("У пользователя нет общих друзей");
            return friendsUser;
        }
        return friendsUser;
    }

    private Long findMaxIdUsers() {
        Long id = 1L;
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
