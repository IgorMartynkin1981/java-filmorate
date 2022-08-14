package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreDAO {
    Genre getById(Long id);

    Collection<Genre> findAll();

    Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres);

    List<Genre> getFilmGenres(Long filmId);

    void deleteFilmGenres(Long filmId);
}
