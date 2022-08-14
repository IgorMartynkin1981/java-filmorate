package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.findUser(userId);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User createFriends(@Valid @PathVariable("userId") Long userId,
                              @Valid @PathVariable("friendId") Long friendId) {
        userService.addFriends(userId, friendId);
        return userService.findUser(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Collection<User>> deleteFriends(@PathVariable("userId") Long userId,
                                                          @PathVariable("friendId") Long friendId) {
        userService.deleteFriends(userId, friendId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping
    public void deleteUser() {
        userService.deleteUsers();
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> findFriendsUser(@PathVariable("userId") Long userId) {
        return userService.findFriendsUser(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> findCommonsFriend(@PathVariable("userId") Long userId,
                                              @PathVariable("otherId") Long otherId) {
        return userService.findCommonsFriend(userId, otherId);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
