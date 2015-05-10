package pl.pwr.shopassistant.shopapiclient;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalTime;

public class TimePeriod {

    @Getter @Setter
    private LocalTime from;

    @Getter @Setter
    private LocalTime to;
}
