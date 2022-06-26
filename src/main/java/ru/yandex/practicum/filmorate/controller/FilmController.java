package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmDateReleaseIsWrongException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private final static LocalDate MIN_DATA_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int id = 1;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на добавление пользователя: " + film);
        if (findName(film)) {
            log.warn("Такой фильм уже существует");
            throw new FilmAlreadyExistException("Такой фильм уже существует");
        }
        if (dateRealiseIsAfter(film)) {
            log.warn("Дата релиза не может быть меньше 28.12.1895 года");
            throw new FilmDateReleaseIsWrongException("Дата релиза не может быть меньше 28.12.1895 года");
        }
        findMaxId();
        film.setId(id);
        films.put(film.getId(), film);
        log.info("Фильм '{}' добавлен", film);
        id++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на изменение данных о фильме: '{}'", film);
        dateRealiseIsAfter(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Изменения успешно внесены");
        } else {
            createFilm(film);
        }
        return film;
    }

    private void findMaxId() {
        if (!films.isEmpty()) {
            for (Film filmIds : films.values()) {
                if (filmIds.getId() >= id) {
                    id = filmIds.getId() + 1;
                }
            }
        }
    }

    private boolean dateRealiseIsAfter(Film film) {
        return film.getReleaseDate().isBefore(MIN_DATA_RELEASE_DATE);
    }

    private boolean findName(Film film) {
        for (Film o : films.values()) {
            if (o.getName().equals(film.getName())) {
                return true;
            }
        }
        return false;
    }
}
