package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;

import java.io.Serializable;
import java.util.List;

public class TimeSlot implements Serializable {
    @Getter @Setter
    private String date;

    @Getter @Setter
    private String from;

    @Getter @Setter
    private String to;

    public TimeSlot() {
    }

    public TimeSlot(LocalDate date, TimePeriod time) {
        this.date = date.toString();
        this.from = time.getFrom().toString();
        this.to = time.getTo().toString();
    }

}
