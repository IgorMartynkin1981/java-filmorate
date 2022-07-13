package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findFilm(Long id) {
        return filmStorage.findFilm(id);
    }

    public Film createFilm(Film film) {
        findNameFilm(film);
        film.setId(findMaxId());
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        if (film.getId() <= 0) {
            throw new FilmNotFoundException("Значение не может быть меньше 0");
        }
        if (findAll().stream().anyMatch(p -> p.getId().equals(film.getId()))) {
            return filmStorage.updateFilm(film);
        } else {
            throw new FilmNotFoundException("Значение не может быть меньше 0");
        }
    }

    private Long findMaxId() {
        Long id = 1L;
        for (Film filmIds : filmStorage.findAll()) {
            if (filmIds.getId() >= id) {
                id = filmIds.getId() + 1;
            }
        }
        return id;
    }

    private void findNameFilm(Film film) {
        for (Film o : filmStorage.findAll()) {
            if (o.getName().equals(film.getName())) {
                log.warn("Фильм {} уже существует", film);
                throw new FilmAlreadyExistException();
            }
        }
    }
}
