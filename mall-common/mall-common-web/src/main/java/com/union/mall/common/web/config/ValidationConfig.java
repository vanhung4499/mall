package com.union.mall.common.web.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Configuration class for runtime parameter validation.
 * Configures a custom validator to enable fail-fast validation.
 *
 * @author vanhung4499
 */
@Configuration
public class ValidationConfig {

    /**
     * Configures a custom validator to enable fail-fast validation.
     *
     * @param autowireCapableBeanFactory AutowireCapableBeanFactory instance for Spring bean autowiring
     * @return Validator instance
     */
    @Bean
    public Validator validator(AutowireCapableBeanFactory autowireCapableBeanFactory) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true) // failFast=true skips further validation upon encountering the first validation failure
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

}
