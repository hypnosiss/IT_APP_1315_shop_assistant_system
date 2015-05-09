package pl.pwr.shopassistant.operationresult;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OperationResult {
    private Integer resultCode;
    private String errorMessage;

    private Map<String, Object> values = new HashMap<String, Object>();

    public Object getValue(String name) {
        return this.values.get(name);
    }

    public void setValue(String name, Object value) {
        values.put(name, value);
    }
}
