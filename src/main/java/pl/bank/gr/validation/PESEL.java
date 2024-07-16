package pl.bank.gr.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = PeselValidator.class)
@Documented
public @interface PESEL {

	String message() default "{PESEL.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}