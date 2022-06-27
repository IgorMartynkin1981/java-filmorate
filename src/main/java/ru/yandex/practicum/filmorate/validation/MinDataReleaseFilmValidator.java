package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDataReleaseFilmValidator implements ConstraintValidator<MinDataReleaseFilmValidation, LocalDate> {
    public boolean isValid(LocalDate date, ConstraintValidatorContext cxt) {
        return date == null || date.isAfter(LocalDate.of(1895, 12, 27));
    }
}