package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.Controller;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Создаём нормального пользователя, возвращается код 200")
    void createUserTest() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Создаём пользователя с пустыми полями, возвращается код 400 или 500")
    void createEmptyUserTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём пользователя с пробелом в login, возвращается код 400 или 500")
    void createUserFailLoginTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore ullamco\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём пользователя с пустым login, возвращается код 400 или 500")
    void createUserEmptyLoginTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \" \",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём пользователя с пустым Name, возвращается код 200")
    void createUserEmptyNameTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore1\",\n" +
                                "  \"name\": \" \",\n" +
                                "  \"email\": \"mail1@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Создаём пользователя с пустым не верным Email, возвращается код 400 или 500")
    void createUserFailEmailTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \" \",\n" +
                                "  \"email\": \"mailmail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \" \",\n" +
                                "  \"email\": \"mail @mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём пользователя ДР > now(), возвращается код 400 или 500")
    void createUserDateAfterNowTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"2222-08-20\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создаём пользователя ДР = now(), возвращается код 400 или 500")
    void createUserDateNowTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"2022-06-25\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }





    @Test
    void updateUser() {
    }

    @Test
    void findAll() {
    }
}