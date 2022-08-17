package ru.yandex.practicum.filmorate.dao.LikeDbStorage;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.Collection;

public interface LikeDAO {
    void addLike(Long filmId, Long userId);
    void removeLike(Long filmId, Long userId);
    Collection<Like> getAllLikes(Long filmId);
}
