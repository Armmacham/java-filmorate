package ru.yandex.practicum.filmorate.annotations;

import ru.yandex.practicum.filmorate.validators.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = LoginValidator.class)
public @interface CorrectLogin {
    String message() default "must not have spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
