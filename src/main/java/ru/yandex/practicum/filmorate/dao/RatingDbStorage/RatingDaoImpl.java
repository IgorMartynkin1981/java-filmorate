package ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class RatingDaoImpl implements RatingDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MPA getById(Long id) {
        String sqlQuery = "SELECT id, name FROM rating_mpa WHERE id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
    }

    @Override
    public Collection<MPA> findAll() {
        String sqlQuery = "select id, name" +
                " from rating_mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    private MPA mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return MPA.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
