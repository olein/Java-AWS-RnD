package com.rnd.aws.configuration;

import javax.validation.*;
import java.util.Set;

/*
Fahim created at 6/18/2022
*/
public class ModelValidator {
  public static void validate(Object target) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Object>> violations = validator.validate(target);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
