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

@Component
@Slf4j
public class FilmDaoImpl implements FilmDAO{
    private final JdbcTemplate jdbcTemplate;
    private final GenreDAO genreDAO;
    private final GenreService genreService;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, GenreDAO genreDAO, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDAO = genreDAO;
        this.genreService = genreService;
    }


    @Override
    public Long addFilmInStorage(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, rating_mpa) "
                + "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void deleteFilmInStorage(Long id) {
        String sqlQuery = "DELETE FROM films WHERE id=?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void updateFilmInStorage(Film film) {
        String sqlQuery = "UPDATE films SET name=?, description=?, release_date=?, duration=?, rating_mpa=? "
            + "WHERE id=?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
    }

    @Override
    public Collection<Film> getAllFilmsInStorage() {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_mpa, rm.name"
                + " FROM films AS f "
                + " JOIN rating_mpa AS rm ON rm.id=f.rating_mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilmInStorage(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);

        if(userRows.next()) {
            Film film = new Film(
                    userRows.getLong("id"),
                    userRows.getString("name"),
                    userRows.getString("description"),
                    userRows.getDate(LocalDate.parse(resultSet.getString("release_date"),

            );

            log.info("Найден пользователь: {} {}", user.getId(), user.getName());
            return user;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sql = "select f.id            as film_id,\n" +
                "       f.name         as film_name,\n" +
                "       f.description  as film_description,\n" +
                "       f.release_date as film_release_date,\n" +
                "       f.duration     as film_duration,\n" +
                "       mr.id          as rating_id,\n" +
                "       mr.name        as rating_name\n" +
                "from films f\n" +
                "         join rating_mpa mr on mr.id = f.rating_mpa\n" +
                "where f.id in (select f.id\n" +
                "               from films f\n" +
                "                        left join likes l on f.id = l.film_id\n" +
                "               group by f.id\n" +
                "               order by count(l.user_id) desc\n" +
                "               limit ?)";
        return jdbcTemplate.query(sql, this::mapRowToObject, count);
    }

    private Film mapRowToObject(ResultSet resultSet, int row) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("film_description"))
                .releaseDate(LocalDate.parse(resultSet.getString("release_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .duration(resultSet.getInt("film_duration"))
                .mpa(RatingMPA.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name")).build()).build();
                //.genres(genreDAO.getAllFilmGenres(resultSet.getLong("id"))).build();
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("film_description"))
                .releaseDate(LocalDate.parse(resultSet.getString("release_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .duration(resultSet.getInt("film_duration"))
                .mpa(RatingMPA.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name")).build()).build();
    }
}
