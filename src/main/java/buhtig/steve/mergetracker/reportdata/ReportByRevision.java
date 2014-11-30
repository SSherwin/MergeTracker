package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;

import java.util.ArrayList;
import java.util.List;


/**
 * The by Revision report creates a report that shows all merged and unmerged revisions
 * between the first unmerged revision and the last unmerged revision.
 * Created by Steve on 30/11/2014.
 */
public class ReportByRevision extends AbstractReport {

    public ReportByRevision(final BranchMergeTracker tracker) {
        super(tracker);

    }

    @Override
    protected List<MergeRevision> createRevisionList(BranchMergeTracker tracker) {
        final List<MergeRevision> revisions = new ArrayList<>();

        // Get the revisions to merge
        final List<Revision> revisionsToMerge = tracker.getRevisionsToMerge();

        // if we have revisions to report
        if (null != revisionsToMerge && revisionsToMerge.size() > 0) {
            /* The report is to report all revision between the first unmerged
             * revision and the last unmerged revision.
             */
            final Long firstRevision = revisionsToMerge.get(0).getRevision();
            final Long lastRevision = revisionsToMerge.get(revisionsToMerge.size() - 1).getRevision();

            // From the list of all revisions pull out those between the first and last.
            final List<Revision> revisionsInRange = tracker.getMergeFrom().getRevisions();
            for (Revision revToReport : revisionsInRange) {
                if (firstRevision <= revToReport.getRevision() && lastRevision >= revToReport.getRevision()) {
                    revisions.add(new MergeRevision(revToReport,
                            revisionsToMerge.contains(revToReport)));
                }
            }
        }
        return revisions;
    }


}
