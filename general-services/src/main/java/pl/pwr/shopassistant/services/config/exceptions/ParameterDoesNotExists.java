package pl.pwr.shopassistant.services.config.exceptions;

public class ParameterDoesNotExists extends RuntimeException {
    public ParameterDoesNotExists(String key) {
        super("Parameter " + key + " not provided");
    }
}
