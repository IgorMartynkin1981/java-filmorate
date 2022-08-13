package ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.List;
import java.util.Optional;

@Component
public class RatingDaoImpl implements RatingDAO{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<RatingMPA> loadMpaById(long id) {
        String sqlQuery = "SELECT id, name FROM rating_mpa WHERE id = ?;";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(RatingMPA.class), id).stream().findAny();
    }

    @Override
    public List<RatingMPA> loadAllMpa() {
        String sqlQuery = "SELECT id, name FROM rating_mpa;";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(RatingMPA.class));
    }
}
