package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDAO {
    void addFilmGenre(Long filmId, Long genreId);
    void removeFilmGenres(Long filmId);
    Collection<Genre> getAllGenres();
    Collection<Genre> getAllFilmGenres(Long filmId);
    Genre getGenreById (Long genreId);
}
