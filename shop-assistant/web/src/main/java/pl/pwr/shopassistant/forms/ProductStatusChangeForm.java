package pl.pwr.shopassistant.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.controllers.api.fridge.ProductStatus;

import javax.validation.constraints.Size;

public class ProductStatusChangeForm extends AbstractForm {

    @Size()
    @Getter @Setter
    private String uuid;

    @Getter @Setter
    private ProductStatus status;

    @Getter @Setter
    private Long timestamp;
}
