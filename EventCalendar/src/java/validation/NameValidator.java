package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Christof Van Cauteren
 */

public class NameValidator implements ConstraintValidator<ValidName, String>
{
    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[a-zA-Z0-9 _]+");
    }
}
