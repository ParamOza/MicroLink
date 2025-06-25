package me.paramoza.microlink.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PublicURLValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicURL {
    String message() default "URL must be a valid public URL (no localhost or private IPs)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
