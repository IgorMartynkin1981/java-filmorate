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
import java.util.stream.Collectors;

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

    public User createFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение id пользователя или друга не может быть меньше 0");
        }
        User frUser = friendDAO.createFriend(userId, friendId);
//        User user = addFriend(userId, friendId);
//        User friend = addFriend(friendId, userId);
        return null;
    }

    private User addFriend(Long userId, Long friendId) {
        Set<Long> friendsId = new TreeSet<>();
        User user = findUser(userId);
//        if (user.getFriends() != null) {
//            friendsId.addAll(user.getFriends());
//        }
//        friendsId.add(friendId);
//        user.setFriends(friendsId);
//        updateUser(user);
        return user;
    }

    public User deleteFriends(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new UserNotFoundException("Значение id пользователя или друга не может быть меньше 0");
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
//        if (user.getFriends() != null) {
//            friendsId.addAll(user.getFriends());
//        } else {
//            log.info("У пользователя {} нет друзей", userStorage.findUser(userId));
//        }
//        friendsId.remove(friendId);
//        user.setFriends(friendsId);
//        updateUser(user);
        return user;
    }

    public Collection<User> findFriendsUser(Long id) {
        if (id <= 0) {
            throw new UserNotFoundException("Значение id пользователя не может быть меньше 0");
        }
        List<User> friendsUser = new ArrayList<>();
//        if (findUser(id).getFriends() != null) {
//            for (Long i : findUser(id).getFriends()) {
//                friendsUser.add(findUser(i));
//            }
//        } else {
//            log.info("У пользователя {} нет друзей", userStorage.findUser(id));
//            return friendsUser;
//        }
        return friendsUser;
    }

    public Collection<User> findCommonsFriend(Long idFirst, Long idSecond) {
        if (idFirst <= 0 || idSecond <= 0) {
            throw new UserNotFoundException("Значение id пользователей не может быть меньше 0");
        }
        List<User> friendsUser = new ArrayList<>();
//        Set<Long> firstUser = findUser(idFirst).getFriends();
//        Set<Long> secondUser = findUser(idSecond).getFriends();
//        if (findUser(idFirst).getFriends() != null || findUser(idSecond).getFriends() != null) {
//            List<Long> common = firstUser.stream().filter(secondUser::contains).collect(Collectors.toList());
//            for (Long i : common) {
//                friendsUser.add(findUser(i));
//            }
//        } else {
//            log.info("У пользователя {} нет общих друзей c пользователем {}."
//                    ,findUser(idFirst).getName()
//                    ,findUser(idSecond).getName());
//            return friendsUser;
//        }
        return friendsUser;
    }

//    private Long findMaxIdUsers() {
//        Long id = 1L;
//        if (!userStorage.findAll().isEmpty()) {
//            for (User userIds : userStorage.findAll()) {
//                if (userIds.getId() >= id) {
//                    id = userIds.getId() + 1;
//                }
//            }
//        }
//        return id;
//    }

    //Перевел в UserDAO
    private void findEmailUser(User user) {
        for (User o : userDAO.findAll()) {
            if (o.getEmail().equals(user.getEmail())) {
                log.warn("Пользователь с электронной почтой {} уже зарегистрирован.", user.getEmail());
                throw new UserAlreadyExistException();
            }
        }
    }

    //Перевел в UserDAO
    private void createNameUserIsEmpty(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    //Перевел в UserDAO
    public void deleteUser(Long id) {
        userDAO.deleteUser(findUser(id));
    }

    //Перевел в UserDAO
    public void deleteUsers() {
        userDAO.deleteUsers();
    }
}
