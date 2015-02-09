package buhtig.steve.mergetracker;

import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.providers.ApplicationConfigurationProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    private ApplicationConfigurationProvider configProvider;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
