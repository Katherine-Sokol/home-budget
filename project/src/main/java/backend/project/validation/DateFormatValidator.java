package backend.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

  private String pattern;

  @Override
  public void initialize(ValidDateFormat constraintAnnotation) {
    this.pattern = constraintAnnotation.pattern();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) return false;

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      LocalDate.parse(value, formatter);
      return true;
    } catch (DateTimeParseException ex) {
      return false;
    }
  }
}