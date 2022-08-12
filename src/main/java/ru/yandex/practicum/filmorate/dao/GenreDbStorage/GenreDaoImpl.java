package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class GenreDaoImpl implements GenreDAO{
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilmGenre(Long filmId, Long genreId) {
        String sqlQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public void removeFilmGenres(Long filmId) {
        String sqlQuery = "DELETE FROM film_genre WHERE film_id= ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public Collection<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public Collection<Genre> getAllFilmGenres(Long filmId) {
        String sql = "SELECT G.id, G.name FROM film_genre AS FG JOIN genre_type AS G "
                + "ON FG.genre_id = G.id WHERE film_id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), filmId);
    }

    @Override
    public Genre getGenreById(Long genreId) {
        String sql = "SELECT * FROM genres WHERE id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId).get(0);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }

}
