package ru.yandex.practicum.filmorate.dao.GenreDbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDaoImplTest {
    private final GenreDAO genreDAO;

    @Test
    void findGenresByIdTest() {
        Optional<Genre> genreOptional = Optional.ofNullable(genreDAO.getById(1L));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre -> assertThat(genre)
                        .hasFieldOrPropertyWithValue("name", "Комедия"));
    }

}