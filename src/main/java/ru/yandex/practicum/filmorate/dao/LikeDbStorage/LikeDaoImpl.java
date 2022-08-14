package ru.yandex.practicum.filmorate.dao.LikeDbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class LikeDaoImpl implements LikeDAO {
    private final JdbcTemplate jdbcTemplate;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO likes VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public Collection<Like> getAllLikes(Long filmId) {
        String sql = "SELECT * FROM likes WHERE film_id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs), filmId);
    }

    public Long getCountLikes() {
        String sql = "SELECT film_id FROM likes GROUP BY film_id ORDER BY COUNT(user_id) DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs)).get(0).getFilmId().longValue();
    }


    private Like makeLike(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long filmId = rs.getLong("film_id");
        Long userId = rs.getLong("user_id");
        return new Like(id, filmId, userId);
    }
}
