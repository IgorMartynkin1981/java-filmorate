package ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RatingDAO {
    Optional<RatingMPA> loadMpaById(long id);

    List<RatingMPA> loadAllMpa();
}
