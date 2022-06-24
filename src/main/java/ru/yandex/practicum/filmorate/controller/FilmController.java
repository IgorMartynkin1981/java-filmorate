package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

@RestController
@RequestMapping("/film")
public class FilmController {
    private final TreeMap<String, User> films = new TreeMap<>((o1, o2) -> {
        if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        }
        return Integer.compare(o1.compareTo(o2), 0);
    });
}
