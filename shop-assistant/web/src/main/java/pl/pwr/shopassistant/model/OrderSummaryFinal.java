package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class OrderSummaryFinal implements Serializable {
    @Getter @Setter
    private String[] eans;

    @Getter @Setter
    private String shopName;

    @Getter @Setter
    private TimeSlot timeSlot;

    public OrderSummaryFinal() {
    }

}
