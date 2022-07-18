package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotLikesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findFilm(Long id) {
        if (id <= 0) {
            throw new FilmNotFoundException("Значение id не может быть меньше 0");
        }
        return filmStorage.findFilm(id);
    }

    public Film createFilm(Film film) {
        findForFilmByName(film);
        film.setId(findMaxId());
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        if (film.getId() <= 0) {
            throw new FilmNotFoundException("Значение id не может быть меньше 0");
        }
        if (findAll().stream().anyMatch(p -> p.getId().equals(film.getId()))) {
            return filmStorage.updateFilm(film);
        } else {
            throw new FilmNotFoundException("Фильм в базе отсутствует");
        }
    }

    private Long findMaxId() {
        Long id = 1L;
        for (Film film : filmStorage.findAll()) {
            if (film.getId() >= id) {
                id = film.getId() + 1;
            }
        }
        return id;
    }

    private void findForFilmByName(Film film) {
        if (findAll().stream().anyMatch(f -> f.getName().equals(film.getName()))) {
            log.warn("Фильм {} уже существует", film);
            throw new FilmAlreadyExistException();
        }
    }

    public Film putLike(Long filmId, Long userId) {
        if (userService.findUser(userId).getId().equals(userId) &&
                filmStorage.findFilm(filmId).getId().equals(filmId)) {
            Film film = findFilm(filmId);
            Set<Long> likeId = new TreeSet<>();
            if (film.getLike() != null) {
                likeId.addAll(film.getLike());
            }
            likeId.add(userId);
            film.setLike(likeId);
            updateFilm(film);
            return film;
        } else {
            throw new FilmNotFoundException("Фильм или пользователь не существует");
        }
    }

    public Film deleteLike(Long filmId, Long userId) {
        if (userService.findUser(userId).getId().equals(userId) &&
                filmStorage.findFilm(filmId).getId().equals(filmId)) {
            Film film = findFilm(filmId);
            Set<Long> likeId = new TreeSet<>();
            if (film.getLike() != null) {
                likeId.addAll(film.getLike());
            } else {
                throw new FilmNotLikesException("У фильма нет лайков.");
            }
            likeId.remove(userId);
            film.setLike(likeId);
            updateFilm(film);
            return film;
        } else {
            throw new FilmNotFoundException("Фильм или пользователь не существует");
        }
    }

    public Collection<Film> findAllByPopularity(Integer count) {
        return findAll().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compare(Film f0, Film f1) {
        int p0;
        int p1;

        if (f0.getLike() == null) {
            p0 = 0;
        } else {
            p0 = f0.getLike().size();
        }
        if (f1.getLike() == null) {
            p1 = 0;
        } else {
            p1 = f1.getLike().size();
        }
        return p1 - p0;
    }
}
