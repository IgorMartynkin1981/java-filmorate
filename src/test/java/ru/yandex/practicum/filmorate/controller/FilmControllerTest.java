package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Создаём фильм, возвращается код 200")
    void createFilmTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Создаём фильм c пустыми полями, возвращается код 400 или 500")
    void createFilmEmptyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём фильм с пустым именем, возвращается код 400 или 500")
    void createFilmEmptyNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём фильм с описанием в 200 символов, возвращается код 200")
    void createFilmDescriptionSize200Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod2\",\n" +
                                "  \"description\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Создаём фильм с описанием в 201 символ, возвращается код 400 или 500")
    void createFilmDescriptionSize201Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod3\",\n" +
                                "  \"description\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                                    "a\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём фильм c датой релиза 28 декабря 1895 года, возвращается код 200")
    void createFilmReleaseDate28121895Test() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod4\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1895-12-28\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Обновляем фильм, возвращается код 200")
    void updateFilmTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Film Updated\",\n" +
                                "  \"releaseDate\": \"1989-04-17\",\n" +
                                "  \"description\": \"New film update decription\",\n" +
                                "  \"duration\": 190,\n" +
                                "  \"rate\": 4\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Обновляем фильм c отрицательным id, возвращается код код 400 или 500")
    void updateFilmSubZeroIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": -1,\n" +
                                "  \"name\": \"Film Updated\",\n" +
                                "  \"releaseDate\": \"1989-04-17\",\n" +
                                "  \"description\": \"New film update decription\",\n" +
                                "  \"duration\": 190,\n" +
                                "  \"rate\": 4\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Get, возвращает код 200")
    void addTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/films").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}