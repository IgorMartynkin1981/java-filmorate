package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findFilm(Long id) {
        return films.get(id);
    }

    @Override
    public Film createFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Фильм '{}' добавлен", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Изменения для фильма {} внесены", film);
        return film;
    }

    @Override
    public void deleteFilms() {
        films.clear();
    }

    @Override
    public void deleteFilm(Film film) {
        films.remove(film.getId());
    }
}
