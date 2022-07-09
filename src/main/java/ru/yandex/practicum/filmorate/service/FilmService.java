package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
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

    public Film findFilm(Integer id) {
        return filmStorage.findFilm(id);
    }

    public Film createFilm(Film film) {
        findNameFilm(film);
        film.setId(findMaxId());
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(@RequestBody Film film) {
        if (filmStorage.findAll().contains(film)) {
            filmStorage.updateFilm(film);
            log.info("Изменения успешно внесены");
        } else {
            createFilm(film);
        }
        return film;
    }

    private Integer findMaxId() {
        int id = 1;
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
