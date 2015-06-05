package pl.pwr.shopassistant.shopapiclient;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JodaLocalTimeJsonSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime localTime, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
        gen.writeString(localTime.toString(fmt));
    }
}
