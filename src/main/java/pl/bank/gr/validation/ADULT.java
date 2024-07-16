package pl.bank.gr.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
@Documented
public @interface ADULT {
	String message() default "{ADULT.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
