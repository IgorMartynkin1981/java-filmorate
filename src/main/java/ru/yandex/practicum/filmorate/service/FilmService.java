package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage.FilmDAO;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage.GenreDAO;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage.LikeDAO;
import ru.yandex.practicum.filmorate.dao.RatingDbStorage.RatingDAO;
import ru.yandex.practicum.filmorate.dao.UserDbStorage.UserDAO;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmDAO filmStorage;
    private final UserDAO userStorage;
    private final GenreDAO genreDbStorage;
    private final RatingDAO ratingDbStorage;
    private final LikeDAO likeDbStorage;

    public Film createFilm(Film film) {
        long filmId = filmStorage.addFilmInStorage(film);
//        if (film.getGenres() != null && film.getGenres().size() > 0) {
//            genreDbStorage.addFilmGenre(filmId, film.getGenres());
//        }
//        Film savedFilm = getFilmOrNotFoundException(filmId);
//        log.debug("Create {}", savedFilm);
        return getFilm(filmId);
    }

    public Collection<Film> getAllFilms() {
        Collection<Film> films;
        films = filmStorage.getAllFilmsInStorage();
        for (Film f : films) {
            Collection<Genre> genres = genreDbStorage.getAllFilmGenres(f.getId());
            Set<Genre> setGenres = new HashSet<Genre>(genres);
            //f.setGenres(setGenres);
            log.info("Подготовка к получению списка фильмов {}      В фильм {} добавлены жанры успешно",
                    LocalDateTime.now(), f.getId());
        }
        if (films != null) {
            log.info("Получение списка фильмов {}      Фильмы получены успешно", LocalDateTime.now());
        } else {
            log.info("Получение списка фильмов {}      Фильмы отсутствуют", LocalDateTime.now());
        }
        return films;
    }

    public Film updateFilm(Film film) {
        filmStorage.updateFilmInStorage(film);
        log.info("Обновление фильма {}      Фильм с ID {} обновлен успешно", LocalDateTime.now(), film.getId());
        genreDbStorage.removeFilmGenres(film.getId());
//        for (Genre g : film.getGenres()) {
//            Genre newGenre = genreDbStorage.getGenreById(g.getId()); //проверка что такой тип жанра есть
//            genreDbStorage.addFilmGenre(film.getId(), newGenre.getId());
//            log.info("Обновление фильма {}      К фильму с ID {} добавлен жанр {} успешно",
//                    LocalDateTime.now(), film.getId(), newGenre.getName());
//        }
        return getFilm(film.getId());
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmInStorage(filmId);
        Like like = new Like(filmId, userId);
        userStorage.findUser(userId); // контроль что такой пользователь есть
        Set<Like> likes = new HashSet<Like>(likeDbStorage.getAllLikes(filmId));
        if (likes.contains(like)) {
            throw new IncorrectParameterException("Ошибка добавления лайка, такой лайк уже проставлен");
        }
        likeDbStorage.addLike(filmId, userId);
        filmStorage.updateFilmInStorage(film);
        log.info("Лайк фильма {}      Пользователь с ID {} проставил лайк на фильм с ID {} успешно",
                LocalDateTime.now(),
                userId, filmId);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmInStorage(filmId);
        Like like = new Like(filmId, userId);
        userStorage.findUser(userId); // контроль что такой пользователь есть
        Set<Like> likes = new HashSet<Like>(likeDbStorage.getAllLikes(filmId));
        if (!likes.contains(like)) {
            throw new IncorrectParameterException("Ошибка удвления лайка, такой лайк не существует");
        }
        likeDbStorage.removeLike(filmId, userId);
        filmStorage.updateFilmInStorage(film);
        log.info("Удаление лайка у фильма {}      Пользователь с ID {} удалил лайк на фильме с ID {} успешно",
                LocalDateTime.now(),
                userId, filmId);
    }

    public List<Film> getTopFilms(int count) {
        try {
            return filmStorage.getPopularFilms(count);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
            throw new NotFoundException(String.format("Ошибка получения %d популярных фильмов", count));
        }
    }

    public Film getFilm(Long id) {
        Film film = filmStorage.getFilmInStorage(id);
//        log.info("Получение фильма {}      Фильм с ID {} получен успешно", LocalDateTime.now(), id);
//        Collection<Genre> genres = genreDbStorage.getAllFilmGenres(id);
//        Set<Genre> setGenres = new HashSet<Genre>(genres);
//        //film.setGenres(setGenres);
//        log.info("Получение фильма {}      Фильм с ID {}, список жанров получен успешно", LocalDateTime.now(), id);
        return film;
    }
}
