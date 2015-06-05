package pl.pwr.shopassistant.services.config;

import pl.pwr.shopassistant.services.config.exceptions.ParameterDoesNotExists;

public interface ConfigService {

    void loadConfiguration();

    boolean exists(String name);

    String getString(String name) throws ParameterDoesNotExists;

    ConfigService setString(String name, String value);

    Integer getInteger(String name);

    Double getDouble(String name);

    Boolean getBoolean(String name);
}
