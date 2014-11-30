package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.BranchMergeTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 30/11/2014.
 */
public abstract class AbstractReport {
    private final String title;

    protected abstract void createReport(final BranchMergeTracker tracker);

    public AbstractReport(final BranchMergeTracker tracker) {
        title = tracker.getTitle();
        createReport(tracker);
    }

    public String getTitle() {
        return title;
    }

}
