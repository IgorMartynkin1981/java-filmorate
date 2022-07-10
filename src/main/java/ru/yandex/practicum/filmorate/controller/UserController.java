package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer id){
        return userService.findUser(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User createFriends(@Valid @PathVariable("userId") Integer userId,
                              @Valid @PathVariable("friendId") Integer friendId) {
        userService.createFriends(userId, friendId);
        return userService.findUser(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFriends(@PathVariable("userId") Integer userId,
                              @PathVariable("friendId") Integer friendId) {
        userService.deleteFriends(userId, friendId);
        return userService.findUser(userId);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> findFriendsUser(@PathVariable("userId") Integer userId) {
        return userService.findFriendsUser(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> findCommonsFriend(@PathVariable("userId") Integer userId,
                                              @PathVariable("otherId") Integer otherId) {
        return userService.findCommonsFriend(userId, otherId);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
