package buhtig.steve.mergetracker.model;

import buhtig.steve.mergetracker.providers.ApplicationConfigurationProvider;

import java.util.List;
import java.util.TreeMap;

/**
 * Merge Tracker model
 * Created by steve on 03/02/15.
 */
public class MergeTracker {

    private  TreeMap<String, Repository>repositories;

    public Repository getRepository(String name) {
        return this.repositories.get(name);
    }

    public void addRepository(Repository repository) {
        this.repositories.put(repository.getName(), repository);
    }
}
