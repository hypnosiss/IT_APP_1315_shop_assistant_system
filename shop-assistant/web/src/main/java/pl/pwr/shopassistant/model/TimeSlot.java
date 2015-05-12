package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;

import java.io.Serializable;
import java.util.List;

public class TimeSlot implements Serializable {
    public static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");

    @Getter @Setter
    private String date;

    @Getter @Setter
    private String from;

    @Getter @Setter
    private String to;

    public TimeSlot() {
    }

    public TimeSlot(LocalDate date, TimePeriod time) {
        this.date = date.toString(dateFormatter);
        this.from = time.getFrom().toString(timeFormatter);
        this.to = time.getTo().toString(timeFormatter);
    }

}
