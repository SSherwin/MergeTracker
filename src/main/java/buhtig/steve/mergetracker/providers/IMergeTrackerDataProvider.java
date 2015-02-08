package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;

import java.util.Map;
import java.util.TreeMap;

/**
 * Interface defining methods the provider of data will implement
 * Created by Steve on 30/11/2014.
 */
public interface IMergeTrackerDataProvider {

    /**
     *
     * @return map of merge reports
     */
    TreeMap<Long, BranchMergeTracker> getMergeData();

    /**
     *
     * @return map of merge reports
     */
    TreeMap<String, Branch> getBranchData();

    /**
     *
     * @param branchMergeTracker tracker to be refreshed
     */
    void refresh(final BranchMergeTracker branchMergeTracker);
}

