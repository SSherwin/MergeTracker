package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.BranchMergeTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 30/11/2014.
 */
public abstract class AbstractReport {
    private final String title;
    private final List<MergeRevision> mergeRevisions;

    protected abstract List<MergeRevision> createRevisionList(final BranchMergeTracker tracker);

    public AbstractReport(final BranchMergeTracker tracker) {
        title = tracker.getTitle();
        mergeRevisions = createRevisionList(tracker);
    }

    public String getTitle() {
        return title;
    }

    public List<MergeRevision> getMergeRevisions() {
        return mergeRevisions;
    }
}
