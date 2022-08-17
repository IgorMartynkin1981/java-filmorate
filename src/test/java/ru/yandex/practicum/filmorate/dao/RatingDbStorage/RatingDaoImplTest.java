package ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RatingDaoImplTest {
    private final RatingDAO ratingDAO;

    @Test
    @DisplayName("Get MPA by id")
    void shouldReturnMpa() {
        Optional<MPA> mpaOptional = Optional.ofNullable(ratingDAO.getById(1L));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    @DisplayName("Get mpa size 5")
    void shouldReturnMpaSize_5() {
        List<MPA> mpa = (List<MPA>) ratingDAO.findAll();
        assertThat(mpa).hasSize(5);
    }

}