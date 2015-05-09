package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    @Getter @Setter
    private String date;

    @Getter @Setter
    private String from;

    @Getter @Setter
    private String to;

    public TimeSlot() {
    }

    public TimeSlot(String date, String from, String to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }
}
