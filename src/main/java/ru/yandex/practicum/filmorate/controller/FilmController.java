package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на добавление фильма {} ", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на изменение данных о фильме: {}", film);
        return filmService.updateFilm(film);
    }

    /*@GetMapping("/popular")
    public Collection<Film> findAllPopular (@RequestParam(defaultValue = "0", required = false) Integer page,
    @RequestParam(defaultValue = "10", required = false) Integer size,
    @RequestParam(defaultValue = DESCENDING_ORDER, required = false) String sort
    ) {

    }*/

}
