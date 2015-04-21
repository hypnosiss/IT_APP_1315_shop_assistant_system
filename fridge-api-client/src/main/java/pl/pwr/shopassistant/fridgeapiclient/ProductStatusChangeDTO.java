package pl.pwr.shopassistant.fridgeapiclient;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
public class ProductStatusChangeDTO {
    private String ean;
    private UUID uuid;
    private ProductStatus status;
    private DateTime dateTime;
}
