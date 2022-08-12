package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
public class GenreDaoImpl implements GenreDAO{
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilmGenre(int filmId, int genreId) {
        String sqlQuery = "insert into film_genre (film_id, genre_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public void removeFilmGenres(int filmId) {

    }

    @Override
    public Collection<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Collection<Genre> getAllFilmGenres(int filmId) {
        return null;
    }

    @Override
    public Genre getGenreById(int genreId) {
        return null;
    }
}
