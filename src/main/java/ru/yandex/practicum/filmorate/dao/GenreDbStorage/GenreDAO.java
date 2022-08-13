package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO {
    Optional<Genre> loadGenreById(Long id);

    List<Genre> loadGenresByFilmId(Long id);

    void saveGenresToFilm(Long id, List<Genre> genres);

    void deleteGenresOfFilm(Long id);

    List<Genre> loadAllGenres();
}
