package ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface RatingDAO {
    MPA getById(Long id);

    Collection<MPA> findAll();
}
