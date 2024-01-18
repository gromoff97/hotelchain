package org.gromovhotels.hotelchain.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BookingCreationValidator.class)
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface ValidatedBookingCreation {
    String message() default "End date must be after begin date and both must be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
