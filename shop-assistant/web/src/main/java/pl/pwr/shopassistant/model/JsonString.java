package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JsonString implements Serializable {

    @Getter @Setter
    private String value;

    public JsonString() {
    }

    public JsonString(String value) {
        this.value = value;
    }
}
