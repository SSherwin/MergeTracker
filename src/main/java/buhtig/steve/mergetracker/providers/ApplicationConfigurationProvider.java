package buhtig.steve.mergetracker.providers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by steve on 13/01/15.
 */
@Component
public class ApplicationConfigurationProvider {


    @Value("#{'${applications}'.split(',')}")
    private List<String> applications;

    private TreeMap<String, PropertiesConfiguration> applicationConfig;


    ApplicationConfigurationProvider() throws ConfigurationException {
        applicationConfig = new TreeMap<>();
    }

    @PostConstruct
    private void loadData() {
        for (String app : applications) {
            try {
                PropertiesConfiguration config = new PropertiesConfiguration(getPropertyFileName(app));
                config.setReloadingStrategy(new FileChangedReloadingStrategy());
                applicationConfig.put(app, config);
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        }
    }


    public PropertiesConfiguration getConfigProperties(final String application) {
        return applicationConfig.get(application);
    }


    private String getPropertyFileName(final String application) {
        return application.toLowerCase() + ".properties";
    }
}
