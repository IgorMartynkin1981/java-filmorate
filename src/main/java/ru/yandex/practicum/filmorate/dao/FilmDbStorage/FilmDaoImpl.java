package ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage.GenreDAO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class FilmDaoImpl implements FilmDAO{
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreService = genreService;
    }


    @Override
    public Optional<Film> loadFilm(Long id) {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa " +
                        "FROM films f " +
                        "JOIN rating_mpa m" +
                        "    ON m.id = f.mpa_id " +
                        "WHERE f.id = ?;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService), id).stream().findAny();
    }

    @Override
    public long saveFilm(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setLong(4, film.getDuration());
            statement.setLong(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateFilm(Film film) {
        String sqlQuery = "UPDATE films " +
                "SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(
                sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
    }

    @Override
    public List<Film> loadFilms() {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa, " +
                        "FROM films f " +
                        "JOIN rating_mpa m" +
                        "    ON m.id = f.mpa_id;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService));
    }

    @Override
    public void saveRatingPoint(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO films_ratings (film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteRatingPoint(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM liles WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public boolean hasFilmRatingFromUser(Long filmId, Long userId) {
        String sqlQuery = "SELECT COUNT(user_id) FROM likes WHERE film_id = ? AND user_id = ?;";
        int rating = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        return rating > 0;
    }

    @Override
    public List<Film> loadPopularFilms(Long count) {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa, " +
                        "FROM films f " +
                        "JOIN rating_mpa m" +
                        "    ON m.id = f.mpa_id " +
                        "LEFT JOIN (SELECT film_id, " +
                        "      COUNT(user_id) rating " +
                        "      FROM likes " +
                        "      GROUP BY film_id " +
                        ") r ON f.id =  r.film_id " +
                        "ORDER BY r.rating DESC " +
                        "LIMIT ?;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService), count);
    }
}
