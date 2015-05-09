package pl.pwr.shopassistant.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterForm extends AbstractForm {

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String username;

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 16)
    @Getter @Setter
    private String repeatPassword;

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
