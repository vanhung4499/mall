package com.hnv99.mall.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation for validating if the user's status is within a specified range
 */
@Documented
@Retention(RetentionPolicy.RUNTIME) // Annotation is available at runtime
@Target({ElementType.FIELD, ElementType.PARAMETER}) // Annotation can be used on fields and parameters
@Constraint(validatedBy = FlagValidatorClass.class) // Specifies the validator class that implements the validation logic
public @interface FlagValidator {
    String[] value() default {}; // The specified range of valid values

    String message() default "flag is not found"; // The default error message if the validation fails

    Class<?>[] groups() default {}; // Specifies the validation groups this validator belongs to

    Class<? extends Payload>[] payload() default {}; // Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint
}
