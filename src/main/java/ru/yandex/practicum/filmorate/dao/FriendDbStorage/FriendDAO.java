package ru.yandex.practicum.filmorate.dao.FriendDbStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendDAO {
    void addFriend(Long userId, Long friendId);
    void deleteFriend(Long userId, Long friendId);
    Collection<User> findFriendsUser(Long id);
    Collection<User> findCommonsFriend(Long idFirst, Long idSecond);
}
