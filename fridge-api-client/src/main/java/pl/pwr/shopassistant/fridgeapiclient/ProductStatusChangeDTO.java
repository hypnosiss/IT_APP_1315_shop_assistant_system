package pl.pwr.shopassistant.fridgeapiclient;

import org.joda.time.DateTime;

import java.util.UUID;

public class ProductStatusChangeDTO {

//    @Getter @Setter
    private UUID uuid;

//    @Getter @Setter
    private ProductStatus status;

//    @Getter @Setter
    private Long timestamp;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
