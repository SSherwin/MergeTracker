package buhtig.steve.mergetracker.manager;

import buhtig.steve.mergetracker.model.MergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.providers.ApplicationConfigurationProvider;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import buhtig.steve.mergetracker.providers.SubversionProvider;
import buhtig.steve.mergetracker.providers.TestDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by steve on 03/02/15.
 */
@Component
public class MergeTrackerManager {

    private ApplicationConfigurationProvider configurationProvider;
    private TreeMap<String, Repository> repositories;

    @Autowired
    MergeTrackerManager(final ApplicationConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        this.repositories = new TreeMap<>();
    }

    public Repository getRepository(final String name) {
        return this.repositories.get(name);
    }

    public void refresh() {
        List<String> apps = configurationProvider.getApplicationList();
        for (String  app : apps) {
            if (getRepository(app) == null) {
                createRepository(app);
            }
        }
    }

    private void createRepository(final String app) {
        final Repository repository = new Repository();
        repository.setName(app);
        repository.setProvider(getProvider(app));
        this.repositories.put(repository.getName(), repository);
    }

    private IMergeTrackerDataProvider getProvider(final String app) {
        final IMergeTrackerDataProvider provider;
        switch (configurationProvider.getType(app)) {
            case "subversion":
                provider = getSubversionProvider();
                break;
            default:
                provider = getTestDataProvider();
        }
        return provider;
    }

    @Bean
    @Scope("prototype")
    private IMergeTrackerDataProvider getTestDataProvider() {
        return new TestDataProvider();
    }

    @Bean
    @Scope("prototype")
    private IMergeTrackerDataProvider getSubversionProvider() {
        return new SubversionProvider();
    }

    public Collection<Repository> getRepositories() {
        return this.repositories.values();
    }
}
