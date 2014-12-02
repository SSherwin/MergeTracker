package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import buhtig.steve.mergetracker.providers.MergeTrackerDataProviderFactory;
import buhtig.steve.mergetracker.reportdata.ReportByBugId;
import buhtig.steve.mergetracker.reportdata.ReportByRevision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service to provide the data in REST format of various merge tracking reports.
 * Created by Steve on 30/11/2014.
 */
@Configurable
@RestController( )
public class MergerTrackerReportServices {

    TreeMap<Long, BranchMergeTracker> reports;

    MergeTrackerDataProviderFactory factory;

    @Autowired
    public MergerTrackerReportServices(final MergeTrackerDataProviderFactory factory) {
        this.factory = factory;
    }

    @PostConstruct
    public void initialise() {
        reports = factory.getProvider().loadData();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/list")
    public Map<Long, String> list() {
        Map<Long, String> result = new TreeMap<>();
        for(BranchMergeTracker tracker : reports.values()) {
            result.put(tracker.getId(), tracker.getTitle());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/{reportId}/byrevision")
    public ReportByRevision reportByRevision(@PathVariable Long reportId) {
        final BranchMergeTracker branchMergeTracker = reports.get(reportId);
        factory.getProvider().refresh(branchMergeTracker);
        return new ReportByRevision(branchMergeTracker);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/mergereport/{reportId}/bybugid")
    public ReportByBugId reportByBugId(@PathVariable Long reportId) {
        final BranchMergeTracker branchMergeTracker = reports.get(reportId);
        factory.getProvider().refresh(branchMergeTracker);
        return new ReportByBugId(branchMergeTracker);
    }

}