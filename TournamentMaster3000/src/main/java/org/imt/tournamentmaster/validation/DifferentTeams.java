package org.imt.tournamentmaster.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // S'applique à la classe (le DTO)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentTeamsValidator.class)
public @interface DifferentTeams {
    String message() default "Les équipes A et B doivent être différentes";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
