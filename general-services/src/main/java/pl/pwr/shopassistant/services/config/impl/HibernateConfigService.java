package pl.pwr.shopassistant.services.config.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.shopassistant.dao.ConfigDao;
import pl.pwr.shopassistant.entities.Config;
import pl.pwr.shopassistant.services.config.ConfigService;
import pl.pwr.shopassistant.services.config.exceptions.ParameterDoesNotExists;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HibernateConfigService implements ConfigService {

	private Map<String, String> parameters = new HashMap<String, String>();

    @Autowired
    @Setter
    private ConfigDao configDao;

    private Map<String, String> getParameters() {
        return parameters;
    }

    @PostConstruct
    public void loadConfiguration() {
        Map<String, String> parameters = new HashMap<String, String>();

        List<Config> configs = configDao.getList();
        for (Config config : configs) {
            parameters.put(config.getName(), config.getValue());
        }

        this.parameters = parameters;
    }

    public boolean exists(String name) {
        Map<String, String> configParamMap = this.getParameters();
        return configParamMap.containsKey(name);
    }

    public String getString(String name) throws ParameterDoesNotExists {
        Map<String, String> parameters = this.getParameters();

        if (!this.exists(name)) {
            throw new ParameterDoesNotExists(name);
        }

        return parameters.get(name);
    }

    public ConfigService setString(String name, String value) {
        Map<String, String> parameters = this.getParameters();

        if (!this.exists(name)) {
            Config config = new Config();
            config.setName(name);
            config.setValue(value);

            configDao.save(config);
        } else {
            Config config = configDao.getConfigByName(name);
            config.setValue(value);

            configDao.update(config);

            parameters.remove(name);
        }

        parameters.put(name, value);

        return this;
    }

    public Integer getInteger(String name) {
        String valueString = this.getString(name);

        return Integer.parseInt(valueString);
    }

    public Double getDouble(String name) {
        String valueString = this.getString(name);

        return Double.parseDouble(valueString);
    }

    public Boolean getBoolean(String name) {
        String valueString = this.getString(name);

        return valueString.equals("1");
    }
}