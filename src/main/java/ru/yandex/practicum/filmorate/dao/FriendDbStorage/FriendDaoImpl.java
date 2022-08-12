package ru.yandex.practicum.filmorate.dao.FriendDbStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDbStorage.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class FriendDaoImpl implements FriendDAO{
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friends (user_id, friend_id, confirming)" +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            stmt.setBoolean(3, true);
            return stmt;
        });
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id=?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public Collection<User> findFriendsUser(Long id) {
        return jdbcTemplate.query(getSqlFriends(), this::mapRowToObject, id);
    }

    private String getSqlFriends() {
        return "SELECT u.id, u.login, u.name, u.email, u.birthday "
                + "FROM users u "
                + "WHERE u.id IN ("
                    + "SELECT f.friend_id "
                    + "FROM friends f "
                    + "WHERE f.user_id = ?)";
    }

    private User mapRowToObject(ResultSet resultSet, int row) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday"))
                .build();
    }

    @Override
    public Collection<User> findCommonsFriend(Long idFirst, Long idSecond) {
        return jdbcTemplate.query(getSqlCommonFriends(), this::mapRowToObject, idFirst, idSecond);
    }

    private String getSqlCommonFriends() {
        return "SELECT u.id, u.login, u.name, u.email, u.birthday FROM users u "
                + "WHERE u.id IN ("
                    + "SELECT f.friend_id FROM friends f "
                    + "WHERE f.user_id = ? INTERSECT "
                        + "SELECT f.friend_id FROM friends f "
                        + "WHERE f.user_id = ?)";
    }
}
