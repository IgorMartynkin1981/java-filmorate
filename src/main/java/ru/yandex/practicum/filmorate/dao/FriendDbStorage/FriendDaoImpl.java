package ru.yandex.practicum.filmorate.dao.FriendDbStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDbStorage.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
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
    public void createFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friends (USER_ID, FRIEND_ID, CONFIRMING)" +
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
    public User deleteFriend(Long userId, Long friendId) {
        return null;
    }

    @Override
    public User deleteFriends(Long userId) {
        return null;
    }

    @Override
    public void deleteFriends() {

    }

    @Override
    public Collection<User> findFriendsUser(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM FRIENDS WHERE USER_ID=?"
                , id
                );
        if(userRows.next()) {
//            User user = new User(
//                    userRows.getLong("id"),
//                    userRows.getString("email"),
//                    userRows.getString("login"),
//                    userRows.getString("name"),
//                    userRows.getDate("birthday")
//            );

            log.info("Запись найдена {}", userRows.getRow());

            return null;
        } else {
            log.info("Пользователь с идентификатором не найден.");
            return null;
        }
        return null;
    }

    @Override
    public Collection<User> findCommonsFriend(Long idFirst, Long idSecond) {
        return null;
    }
}
