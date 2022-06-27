package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target( {FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MinDataReleaseFilmValidator.class)
public @interface MinDataReleaseFilmValidation {
    String message() default "Movie should be released after 1895-12-28";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
