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

import java.util.List;

/**
 * Created by steve on 03/02/15.
 */
public class MergeTrackerManager {

    @Autowired
    private ApplicationConfigurationProvider configurationProvider;

    private MergeTracker mergeTracker;



    public void refresh() {
        List<String> apps = configurationProvider.getApplicationList();
        for (String  app : apps) {
            if (mergeTracker.getRepository(app) == null) {
                createRepository(app);
            }
        }


        for (Repository repository : mergeTracker.getRepositories()) {
            refreshRepository(repository);
        }
    }

    private void createRepository(String app) {
        final Repository repository = new Repository();
        repository.setName(app);


        repository.setDataProvider(getProvider(app));
        this.mergeTracker.addRepository(repository);
    }

    private void refreshRepository(final Repository) {


    }

    private IMergeTrackerDataProvider getProvider(final String app) {

        IMergeTrackerDataProvider provider;

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
    public IMergeTrackerDataProvider getTestDataProvider() {
        return new TestDataProvider();
    }

    @Bean
    @Scope("prototype")
    public IMergeTrackerDataProvider getSubversionProvider() {
        return new SubversionProvider();
    }



}
