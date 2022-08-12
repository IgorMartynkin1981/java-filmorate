package ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Objects;

@Component
@Slf4j
public class FilmDaoImpl implements FilmDAO{
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> findAll() {
        return null;
    }

    @Override
    public Film findFilm(Long id) {
        return null;
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films (name, description, release_date, duration, rating_id)"
                + " values (?, ?, ?, ?, ?)";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"id"});
                statement.setString(1, film.getName());
                statement.setString(2, film.getDescription());
                statement.setDate(3, Date.valueOf(film.getReleaseDate()));
                statement.setInt(4, film.getDuration());
                statement.setLong(5, film.getRatingMPA().getId());
                return statement;
            }, keyHolder);

            film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return film;
            //return addGenresToFilm(film, keyHolder);

        } catch (Exception e) {
            throw new IncorrectParameterException("Получены некорректные данные");
        }
    }


    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void deleteFilms() {

    }

    @Override
    public void deleteFilm(Film film) {

    }
}
