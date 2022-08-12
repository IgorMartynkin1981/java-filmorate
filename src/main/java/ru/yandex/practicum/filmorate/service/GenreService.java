package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage.GenreDAO;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private GenreDAO genreDAO;

    public Collection<Genre> getAllGenres() {
        log.info("Получение списка жанров {}      Жанры получены успешно",
                LocalDateTime.now());
        return genreDAO.getAllGenres();
    }

    public Genre getGenreById(Long id) {
        log.info("Получение жанра по ID {}      Жанр c ID {} получен успешно",
                LocalDateTime.now(), id);
        return genreDAO.getGenreById(id);
    }
}
