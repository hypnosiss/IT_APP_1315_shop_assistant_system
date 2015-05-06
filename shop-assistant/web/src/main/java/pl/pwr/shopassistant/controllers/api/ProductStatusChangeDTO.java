package pl.pwr.shopassistant.controllers.api;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
public class ProductStatusChangeDTO {
    private String uuid;
    private ProductStatus status;
    private Long timestamp;
}
