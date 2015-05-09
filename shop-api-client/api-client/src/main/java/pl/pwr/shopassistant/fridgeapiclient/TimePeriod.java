package pl.pwr.shopassistant.fridgeapiclient;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalTime;

public class TimePeriod {

    @Getter @Setter
    private LocalTime from;

    @Getter @Setter
    private LocalTime to;
}
