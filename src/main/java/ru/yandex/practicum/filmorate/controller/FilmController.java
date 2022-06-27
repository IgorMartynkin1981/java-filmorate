package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на добавление фильма {} ", film);
        findNameFilm(film);
        findMaxId();
        film.setId(id);
        films.put(film.getId(), film);
        log.info("Фильм '{}' добавлен", film);
        id++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос к эндпоинту на изменение данных о фильме: {}", film);
        if (film.getId() < 0) {
            log.warn("id фильма {} не может быть меньше 0", film);
            throw new Exception();
        }
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Изменения успешно внесены");
        } else {
            createFilm(film);
        }
        return film;
    }

    private void findMaxId() {
        for (Film filmIds : films.values()) {
            if (filmIds.getId() >= id) {
                id = filmIds.getId() + 1;
            }
        }
    }

    private void findNameFilm(Film film) {
        for (Film o : films.values()) {
            if (o.getName().equals(film.getName())) {
                log.warn("Фильм {} уже существует", film);
                throw new FilmAlreadyExistException();
            }
        }
    }
}
