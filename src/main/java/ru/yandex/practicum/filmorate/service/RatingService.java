package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingDbStorage.RatingDAO;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

@Service
@Slf4j
public class RatingService {
    private final RatingDAO ratingDAO;

    @Autowired
    public RatingService(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    public MPA getById(long id) {
        if (id < 0) {
            throw new MpaNotFoundException("id должен быть положительным.");
        }
        return ratingDAO.getById(id);
    }

    public Collection<MPA> getAllMpa() {
        Collection<MPA> mpa = ratingDAO.findAll();
        log.debug("Load {} MPA", mpa.size());
        return mpa;
    }
}
