package buhtig.steve.mergetracker.providers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Class to enable the configuration data that relates to the applications tracked and the branches tracked within
 * them to be configured in a way that allows the to be reloaded on change.
 *
 *   <?xml version="1.0" encoding="UTF-8" ?>
 *   <config>
 *   <applications>
 *      <application>
 *         <name>Test1</name>
 *         <url>http://localhost:8080/repos/Test1</url>
 *         <merges>
 *           <merge>A|B</merge>
 *           <merge>A|C</merge>
 *           <merge>A|D</merge>
 *           <merge>D|A</merge>
 *         </merges>
 *      </application>
 *    <application>
 *   </config>
 *
 *
 * Created by steve on 13/01/15.
 */
@Component
public class ApplicationConfigurationProvider {

    public static final String APPLICATIONS_APPLICATION = "applications.application";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String MERGES_MERGE = "merges.merge";
    public static final String TYPE = "reposType";


    private TreeMap<String, HierarchicalConfiguration> applicationConfig;
    private XMLConfiguration config;

    @Autowired
    public ApplicationConfigurationProvider(@Value("${application.config:config.xml}") String configFile) throws ConfigurationException {
        config = new XMLConfiguration(configFile);
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        processProperties(config);
    }


    public Object getUrl(final String application) {
        refreshIfRequired();
        return applicationConfig.get(application).getProperty(URL);
    }


    public List<String>getApplicationList() {
        refreshIfRequired();
        return new ArrayList<String>(applicationConfig.keySet());
    }

    public List<String>getMergeList(final String application) {
        refreshIfRequired();

        final List<Object> list = applicationConfig.get(application).getList(MERGES_MERGE);
        final List<String> newList = new ArrayList<>();
        for (Object obj : list) {
             newList.add((String)obj);
        }
        return newList;
    }

    public String getType(final String application) {
        refreshIfRequired();
        return (String) applicationConfig.get(application).getProperty(TYPE);
    }


    private void processProperties(final XMLConfiguration config) {
        applicationConfig = new TreeMap<>();
        List<HierarchicalConfiguration> applications = config.configurationsAt(APPLICATIONS_APPLICATION);
        for(HierarchicalConfiguration applicationSub : applications) {
            applicationConfig.put((String)applicationSub.getProperty(NAME), applicationSub);

        }
    }

    private void refreshIfRequired() {
        if (config.getReloadingStrategy().reloadingRequired()) {
            processProperties(config);
        }
    }
}
