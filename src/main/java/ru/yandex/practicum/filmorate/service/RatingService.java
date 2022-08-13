package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingDbStorage.RatingDAO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RatingService {
    private final RatingDAO ratingDAO;

    @Autowired
    public RatingService(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    public RatingMPA getMpaOrNotFoundException(long id) {
        Optional<RatingMPA> mpa = ratingDAO.loadMpaById(id);
        if (mpa.isPresent()) {
            log.debug("Load {}", mpa.get());
            return mpa.get();
        } else {
            throw new NotFoundException("MPA #" + id + " not found");
        }
    }

    public List<RatingMPA> getAllMpa() {
        List<RatingMPA> mpa = ratingDAO.loadAllMpa();
        log.debug("Load {} MPA", mpa.size());
        return mpa;
    }
}
