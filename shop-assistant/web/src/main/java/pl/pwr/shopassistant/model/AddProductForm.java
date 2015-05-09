package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import pl.pwr.shopassistant.forms.AbstractForm;

import javax.validation.constraints.NotNull;

public class AddProductForm extends AbstractForm {

    @NotEmpty
    @NotNull
    @Getter @Setter
    private String ean;
}
