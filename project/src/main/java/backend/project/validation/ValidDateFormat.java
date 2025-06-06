package backend.project.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
  String message() default "Date must be in the format yyyy-MM-dd";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  // Optional: Allow custom pattern
  String pattern() default "yyyy-MM-dd";
}