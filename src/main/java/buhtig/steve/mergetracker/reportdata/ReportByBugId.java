package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * The by Revision report creates a report that shows all merged and unmerged revisions
 * between the first unmerged revision and the last unmerged revision.
 * Created by Steve on 30/11/2014.
 */
public class ReportByBugId extends AbstractReport {

    private Map<Long, List<MergeRevision>> dataMap;
    public ReportByBugId(final BranchMergeTracker tracker) {
        super(tracker);

    }

    @Override
    protected void createReport(BranchMergeTracker tracker) {
        dataMap = new TreeMap<>();

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
                    List<MergeRevision> bugRevList = dataMap.get(revToReport.getBugTrackId());
                    if (null == bugRevList) {
                        bugRevList = new ArrayList<>();
                        dataMap.put(revToReport.getBugTrackId(), bugRevList);
                    }
                    bugRevList.add(new MergeRevision(revToReport,
                            revisionsToMerge.contains(revToReport)));
                }
            }
        }
    }


    public Map<Long, List<MergeRevision>> getDataMap() {
        return dataMap;
    }

}
