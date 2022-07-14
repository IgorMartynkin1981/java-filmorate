package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    @DisplayName("Создаём фильм, возвращается код 200")
    void createFilmTest() throws Exception {
        Film film = new Film(1L
                , "Film1"
                , ""
                , LocalDate.now()
                , 45
                , Set.of());
        Mockito.when(filmService.findFilm(1L)).thenReturn(film);

        var responseJson = mockMvc.perform(get("/films/1"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(film, objectMapper.readValue(responseJson, Film.class));
    }

}