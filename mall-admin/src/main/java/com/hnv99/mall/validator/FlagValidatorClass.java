package com.hnv99.mall.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Status flag validator
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {
    private String[] values; // The valid values specified in the annotation

    @Override
    public void initialize(FlagValidator flagValidator) {
        // This method is called when the validator is initialized.
        // It retrieves the valid values from the annotation.
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        // This method is called to validate a specific value.
        // It checks if the value is one of the valid values specified in the annotation.
        boolean isValid = false;
        for (String s : values) {
            if (s.equals(String.valueOf(value))) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
