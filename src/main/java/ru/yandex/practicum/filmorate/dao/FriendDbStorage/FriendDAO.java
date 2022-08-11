package ru.yandex.practicum.filmorate.dao.FriendDbStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendDAO {
    void createFriend(Long userId, Long friendId);
    User deleteFriend(Long userId, Long friendId);
    User deleteFriends(Long userId);
    void deleteFriends();
    Collection<User> findFriendsUser(Long id);
    Collection<User> findCommonsFriend(Long idFirst, Long idSecond);
}
