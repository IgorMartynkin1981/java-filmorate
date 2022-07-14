package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Создаём нормального пользователя, возвращается код 200")
    void createUserTest() throws Exception {
        User user = new User(1L
                , "user1@mail.com"
                , "User1"
                , "User1"
                , LocalDate.of(1981, 12, 23)
                , Set.of());
        Mockito.when(userService.findUser(1L)).thenReturn(user);

        var responseJson = mockMvc.perform(get("/users/1"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(user, objectMapper.readValue(responseJson, User.class));
    }
}