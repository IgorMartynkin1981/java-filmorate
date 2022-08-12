package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDAO {
    void addFilmGenre(int filmId, int genreId);
    void removeFilmGenres(int filmId);
    Collection<Genre> getAllGenres();
    Collection<Genre> getAllFilmGenres(int filmId);
    Genre getGenreById (int genreId);
}
