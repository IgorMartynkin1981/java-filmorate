package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> findAll();
    Film findFilm(Long id);
    Film createFilm(Film film);
    Film updateFilm(Film film);
    void deleteFilms();
    void deleteFilm(Film film);
}
