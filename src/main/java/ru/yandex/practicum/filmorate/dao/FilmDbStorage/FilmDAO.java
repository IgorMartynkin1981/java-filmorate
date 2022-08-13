package ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmDAO {
    Optional<Film> loadFilm(Long id);

    long saveFilm(Film film);

    void updateFilm(Film film);

    List<Film> loadFilms();

    void saveRatingPoint(Long filmId, Long userId);

    void deleteRatingPoint(Long filmId, Long userId);

    boolean hasFilmRatingFromUser(Long filmId, Long userId);

    List<Film> loadPopularFilms(Long count);
}
