package pl.pwr.shopassistant.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ChangeSettingsForm extends AbstractForm {

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String name;

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String address;

    @Getter @Setter
    private String phone;
}
