package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Repository;

import java.util.Map;
import java.util.TreeMap;

/**
 * Interface defining methods the provider of data will implement
 * Created by Steve on 30/11/2014.
 */
public interface IMergeTrackerDataProvider {

    /**
     * @param repository to refresh
     */
    void refresh(final Repository repository);
}

