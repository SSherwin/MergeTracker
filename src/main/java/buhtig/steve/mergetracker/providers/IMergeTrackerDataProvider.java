package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.BranchMergeTracker;

import java.util.TreeMap;

/**
 * Created by Steve on 30/11/2014.
 */
public interface IMergeTrackerDataProvider {

    /**
     *
     * @return map of merge reports
     */
    TreeMap<Long, BranchMergeTracker> loadData();

    /**
     *
     * @param branchMergeTracker tracker to be refreshed
     */
    void refresh(final BranchMergeTracker branchMergeTracker);
}

