package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(Long id) {
        String sqlQuery = "SELECT id, name FROM genres WHERE id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public Collection<Genre> findAll() {
        String sqlQuery = "SELECT id, name FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres) {
        deleteFilmGenres(filmId);
        for (int i = 0; i < genres.size(); i++) {
            if (!getFilmsGenres(filmId, genres.get(i).getId())) {
                addFilmGenres(filmId, genres.get(i).getId());
            }
        }
        return genres;
    }

    private boolean getFilmsGenres(Long filmId, Long genreId) {
        String sqlQuery = "SELECT COUNT(genre_id) FROM films_genres WHERE film_id = ? AND genre_id = ?;";
        int rating = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, genreId);
        return rating > 0;
    }

    private void addFilmGenres(Long filmId, Long genresId) {
        String sqlQuery = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, genresId);
    }

    @Override
    public List<Genre> getFilmGenres(Long filmId) {
        String sqlQuery = "SELECT g.id, g.name\n" +
                "FROM genres AS g\n" +
                "RIGHT JOIN films_genres AS fg ON g.id = fg.genre_id\n" +
                "WHERE fg.film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
    }

    @Override
    public void deleteFilmGenres(Long filmId) {
        String sqlQueryDel = "DELETE FROM films_genres WHERE film_id=?";
        jdbcTemplate.update(sqlQueryDel, filmId);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
