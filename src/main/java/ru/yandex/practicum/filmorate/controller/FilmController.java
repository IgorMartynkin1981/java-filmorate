package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return filmService.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable("filmId") Long filmId) {
        return filmService.getFilm(filmId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на добавление фильма {} ", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту на изменение данных о фильме: {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<HttpStatus> addLike(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        filmService.addLike(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}/like/{userId}")
    public ResponseEntity<HttpStatus> deleteLike(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        filmService.removeLike(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/popular")
    public Collection<Film> findAllByPopularity(@RequestParam(defaultValue = "10", required = false) Integer count) {
        return filmService.getTopFilms(count);
    }

}
