package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage.GenreDAO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreService {
    private final GenreDAO genreDAO;

    @Autowired
    public GenreService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public Genre getGenreOrNotFoundException(Long id) {
        Optional<Genre> genre = genreDAO.loadGenreById(id);
        if (genre.isPresent()) {
            log.debug("Load {}", genre.get());
            return genre.get();
        } else {
            throw new NotFoundException("Genre #" + id + " not found");
        }
    }

    public List<Genre> getFilmGenresById(Long id) {
        return genreDAO.loadGenresByFilmId(id);
    }

    public void addFilmGenres(Long id, List<Genre> genres) {
        genreDAO.saveGenresToFilm(id, genres);
    }

    public void updateFilmGenres(Long id, List<Genre> genres) {
        genreDAO.deleteGenresOfFilm(id);
        genreDAO.saveGenresToFilm(id, genres);
    }

    public void deleteFilmGenres(Long id) {
        genreDAO.deleteGenresOfFilm(id);
    }

    public List<Genre> getAllGenres() {
        List<Genre> genre = genreDAO.loadAllGenres();
        log.debug("Load {} genres", genre.size());
        return genre;
    }
}
