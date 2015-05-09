package pl.pwr.shopassistant.controllers.api.fridge;

import lombok.Data;

@Data
public class ProductStatusChangeDTO {
    private String uuid;
    private ProductStatus status;
    private Long timestamp;
}
