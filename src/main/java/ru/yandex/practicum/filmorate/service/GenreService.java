package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage.GenreDAO;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class GenreService {
    private final GenreDAO genreDAO;

    @Autowired
    public GenreService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public Genre getById(Long id) {
        if (id < 0) {
            throw new GenreNotFoundException("id должен быть больше 0.");
        }
        return genreDAO.getById(id);
    }

    public Collection<Genre> findAll() {
        return genreDAO.findAll();
    }

    public Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres) {
        return genreDAO.addFilmGenres(filmId, genres);
    }

    public List<Genre> getFilmGenres(Long filmId) {
        return genreDAO.getFilmGenres(filmId);
    }

    public void deleteFilmGenres(Long filmId) {
        genreDAO.deleteFilmGenres(filmId);
    }
}
