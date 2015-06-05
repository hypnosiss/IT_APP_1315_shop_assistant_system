package pl.pwr.shopassistant.shopapiclient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalTime;

public class TimePeriod {

    @Getter @Setter
    @JsonSerialize(using = JodaLocalTimeJsonSerializer.class)
    private LocalTime from;

    @Getter @Setter
    @JsonSerialize(using = JodaLocalTimeJsonSerializer.class)
    private LocalTime to;

    @Override
    public String toString() {
        return String.format("%s - %s", from.toString(), to.toString());
    }
}
