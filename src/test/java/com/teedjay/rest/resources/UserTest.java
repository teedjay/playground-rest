package com.teedjay.rest.resources;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Makes sure bean validation constraints works as expected.
 *
 * @author thore johnsen
 */
public class UserTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void allowUserWithoutText() {
        User user = new User("ola", 37, "lt10chars", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User without text should be allowed", 0, violations.size());
    }

    @Test
    public void allowUserWithText() {
        User user = new User("ola", 37, "lt10chars", "some crazy long text goes here");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User with text should be allowed", 0, violations.size());
    }

    @Test
    public void checkMandatoryFields() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Expect 3 violations, since there are 3 mandatory fields", 3, violations.size());
    }

    @Test
    public void checkAllConstraints() {
        User user = new User("ol", -1, "moreThanTenCharacters", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Expect 3 violations since we break 3 rules", 3, violations.size());
    }

    @Test
    public void checkName() {
        User user = new User("ol", 37, "lt10chars", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Expected  only one violation", 1, violations.size());
        violations.forEach(v -> assertEquals("size must be between 3 and 2147483647", v.getMessage()));
    }

    @Test
    public void checkAge() {
        User user = new User("ole", -1, "lt10chars", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Expected  only one violation", 1, violations.size());
        violations.forEach(v -> assertEquals("the value must be positive", v.getMessage()));
    }

    @Test
    public void checkAddress() {
        User user = new User("ole", 37, "moreThanTenCharacters", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Expected  only one violation", 1, violations.size());
        violations.forEach(v -> assertEquals("size must be between 0 and 10", v.getMessage()));
    }

}
