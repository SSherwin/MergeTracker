package buhtig.steve.mergetracker.providers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TreeMap;

/**
 * Class to enable the configuration data that relates to the applications tracked and the branches tracked within
 * them to be configured in a way that allows the to be reloaded on change.
 *
 * <?xml version="1.0" encoding="UTF-8" ?>
 *<config>
 <applications>
 <application>
 <name>Test1</name>
 <url>http://localhost:8080/repos/Test1</url>
 <merges>
 <merge>A|B</merge>
 <merge>A|C</merge>
 <merge>A|D</merge>
 <merge>D|A</merge>
 </merges>
 </application>
 <application>
 <name>Test2</name>
 <url>http://localhost:8080/repos/Test1</url>
 <merges>
 <merge>A|B</merge>
 <merge>A|B</merge>
 <merge>A|B</merge>
 </merges>
 </application>
 </applications>
 </config>
 *
 *
 * Created by steve on 13/01/15.
 */
@Component
public class ApplicationConfigurationProvider {

    private TreeMap<String, HierarchicalConfiguration> applicationConfig;
    private XMLConfiguration config;

    ApplicationConfigurationProvider() throws ConfigurationException {
        config = new XMLConfiguration("config.xml");
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        processProperties(config);

    }

    private void processProperties(XMLConfiguration config) {
        applicationConfig = new TreeMap<>();
        List<HierarchicalConfiguration> applications = config.configurationsAt("applications.application");
        for(HierarchicalConfiguration applicationSub : applications) {
            applicationConfig.put((String)applicationSub.getProperty("name"), applicationSub);

        }
    }

    public Object getConfigProperties(final String application, String key) {
        if (config.getReloadingStrategy().reloadingRequired()) {
            processProperties(config);
        }
        return applicationConfig.get(application).getProperty(key);
    }

}
