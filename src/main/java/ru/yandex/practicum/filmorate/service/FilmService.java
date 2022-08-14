package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage.FilmDAO;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class FilmService {
    private final FilmDAO filmStorage;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmDAO filmStorage, UserService userService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreService = genreService;
    }

    public Film getFilm(Long id) {
        if (id < 0) {
            throw new FilmNotFoundException("id должно быть положительным");
        }
        Film film = filmStorage.getFilm(id);
        List<Genre> genres = genreService.getFilmGenres(film.getId());
        if (genres.size() > 0) {
            film.setGenres(genres);
        }
        return film;
    }

    public Film create(Film film) {
        filmStorage.create(film);
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            List<Genre> genres = new ArrayList<>();

            for (int i = 0; i < film.getGenres().size(); i++) {
                genres.add(genreService.getById(film.getGenres().get(i).getId()));
            }
            genreService.addFilmGenres(film.getId(), genres);
            film.setGenres(genreService.getFilmGenres(film.getId()));
        }
        Film savedFilm = getFilm(film.getId());
        log.debug("Create {}", savedFilm);
        return film;
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            throw new FilmNotFoundException("id фильма не может быть отрицательным");
        }
        filmStorage.updateFilm(film);
        Film updateFilm = getFilm(film.getId());
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            List<Genre> genres = new ArrayList<>();

            for (int i = 0; i < film.getGenres().size(); i++) {
                genres.add(genreService.getById(film.getGenres().get(i).getId()));
            }
            genreService.addFilmGenres(updateFilm.getId(), genres);
            updateFilm.setGenres(genreService.getFilmGenres(film.getId()));
        } else {
            genreService.deleteFilmGenres(updateFilm.getId());
            updateFilm.setGenres(genreService.getFilmGenres(film.getId()));
        }
        log.debug("Update {}", film);
        return updateFilm;
    }

    public List<Film> getAllFilms() {
        List<Film> films = filmStorage.loadFilms();
        log.debug("Load {} movies", films.size());
        return films;
    }

    public void addRatingPoint(Long filmId, Long userId) {
        getFilm(filmId);
        userService.findUser(userId);
        if (filmStorage.hasFilmRatingFromUser(filmId, userId)) {
            log.debug("Attempt to create an existing rating point for movie #{} from user #{}", filmId, userId);
        } else {
            filmStorage.saveRatingPoint(filmId, userId);
            log.debug("Creating rating point for movie #{} from user #{}", filmId, userId);
        }
    }

    public void deleteRatingPoint(Long filmId, Long userId) {
        getFilm(filmId);
        userService.findUser(userId);
        if (filmStorage.hasFilmRatingFromUser(filmId, userId)) {
            filmStorage.deleteRatingPoint(filmId, userId);
            log.debug("Delete rating point of movie #{} from user #{}", filmId, userId);
        } else {
            log.debug("Attempt to delete a non-existent rating point for movie #{} from user #{}", filmId, userId);
        }
    }

    public List<Film> getPopular(Long count) {
        List<Film> popular = filmStorage.loadPopularFilms(count);
        log.debug("Return {} popular films", popular.size());
        return popular;
    }
}
