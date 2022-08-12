package ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDAO {
    Collection<Film> findAll();

    Film findFilm(Long id);

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilms();

    void deleteFilm(Film film);
}
