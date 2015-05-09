package pl.pwr.shopassistant.forms;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class AbstractForm {
    public boolean isValid(BindingResult result) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AbstractForm>> errors = validator.validate(this);

        for (ConstraintViolation<? extends AbstractForm> error : errors) {
            String objectName = result.getObjectName();
            String propertyName = error.getPropertyPath().toString();
            String message = error.getMessage();

            result.addError(new FieldError(objectName, propertyName, message));
        }

        return errors.isEmpty();
    }
}
