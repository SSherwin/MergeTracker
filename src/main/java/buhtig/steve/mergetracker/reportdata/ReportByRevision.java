package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Steve on 30/11/2014.
 */
public class ReportByRevision {
    private final String title;
    private final List<MergeRevision> mergeRevisions;

    public ReportByRevision(BranchMergeTracker tracker) {
        title = tracker.getTitle();
        mergeRevisions = new ArrayList<>();

        // Get the revisions to merge
        final List<Revision> revisonsToMerge = tracker.getRevisionsToMerge();

        // if we have revisions to report
        if (null != revisonsToMerge && revisonsToMerge.size() > 0) {
            /* The report is to report all revision between the first unmerged
             * revision and the last unmerged revision.
             */
            final Long firstRevision = revisonsToMerge.get(0).getRevision();
            final Long lastRevision = revisonsToMerge.get(revisonsToMerge.size() - 1).getRevision();

            // From the list of all revisions pull out those between the first and last.
            final List<Revision> revisionsInRange = tracker.getMergeFrom().getRevisions();
            for (Revision revToReport : revisionsInRange) {
                if (firstRevision <= revToReport.getRevision() && lastRevision >= revToReport.getRevision()) {
                    mergeRevisions.add(new MergeRevision(revToReport,
                            revisonsToMerge.contains(revToReport)));
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public List<MergeRevision> getMergeRevisions() {
        return mergeRevisions;
    }

}
