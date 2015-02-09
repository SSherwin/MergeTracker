package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.manager.MergeTrackerManager;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.reportdata.ReportByBugId;
import buhtig.steve.mergetracker.reportdata.ReportByRevision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Service to provide the data in REST format of various merge tracking reports.
 * Created by Steve on 30/11/2014.
 */
@Configurable
@RestController( )
public class MergerTrackerReportServices {

    private MergeTrackerManager mergeTrackManager;

    @Autowired
    public MergerTrackerReportServices(final MergeTrackerManager mergeTrackManager) {
        this.mergeTrackManager = mergeTrackManager;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/{applicationName}/list")
    public Map<Long, String> list(@PathVariable String applicationName) {
        Map<Long, String> result = new TreeMap<>();

        final Repository repository = mergeTrackManager.getRepository(applicationName);
        if (null != repository) {
            for (BranchMergeTracker tracker : repository.getMerges()) {
                result.put(tracker.getId(), tracker.getTitle());
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/{applicationName}/{reportId}/byrevision")
    public ReportByRevision reportByRevision(@PathVariable String applicationName, @PathVariable Long reportId) {
        final Repository repository = mergeTrackManager.getRepository(applicationName);
        final BranchMergeTracker branchMergeTracker = repository.getMerge(reportId);
        //factory.getProvider().refresh(branchMergeTracker);
        return new ReportByRevision(branchMergeTracker);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/{applicationName}/{reportId}/bybugid")
    public ReportByBugId reportByBugId(@PathVariable String applicationName, @PathVariable Long reportId) {
        final Repository repository = mergeTrackManager.getRepository(applicationName);
        final BranchMergeTracker branchMergeTracker = repository.getMerge(reportId);
        //factory.getProvider().refresh(branchMergeTracker);
        return new ReportByBugId(branchMergeTracker);
    }

}