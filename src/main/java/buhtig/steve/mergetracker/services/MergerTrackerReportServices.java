package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import buhtig.steve.mergetracker.reportdata.ReportByRevision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Steve on 30/11/2014.
 */
@Configurable
@RestController( )
public class MergerTrackerReportServices {

    TreeMap<Long, BranchMergeTracker> reports;

    IMergeTrackerDataProvider provider;

    @Autowired
    MergerTrackerReportServices(final IMergeTrackerDataProvider provider) {
        this.provider = provider;
        reports = provider.loadData();
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
        return new ReportByRevision(reports.get(reportId));
    }

}