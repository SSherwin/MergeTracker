package buhtig.steve.mergetracker.providers;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ssherwin on 02/12/2014.
 */
@Component
@Configuration
public class MergeTrackerDataProviderFactory {

    @Value("${revisiondata.provider:testDataProvider}")
    public String revisionDataProviderConfig;

    @Value("${revisiondata.url}")
    public String url;


    private IMergeTrackerDataProvider provider = null;


    public IMergeTrackerDataProvider getProvider() {
        if (provider == null) {
            switch (revisionDataProviderConfig) {
                case "subversionProvider":
                    provider = getSubversionProvider();
                    break;
                default:
                    provider = getTestDataProvider();
            }
        }
        return provider;
    }

    @Bean
    @Scope("prototype")
    public IMergeTrackerDataProvider getTestDataProvider() {
        return new TestDataProvider(null);
    }

    @Bean
    @Scope("prototype")
    public IMergeTrackerDataProvider getSubversionProvider() {
        return new SubversionProvider();
    }
}
