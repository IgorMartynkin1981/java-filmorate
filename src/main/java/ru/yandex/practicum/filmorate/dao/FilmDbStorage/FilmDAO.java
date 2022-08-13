package ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmDAO {
    Long addFilmInStorage(Film film);

    void deleteFilmInStorage(Long id);

    void updateFilmInStorage(Film film);

    Collection<Film> getAllFilmsInStorage();

    Film getFilmInStorage(Long id);

    List<Film> getPopularFilms(int count);
}
