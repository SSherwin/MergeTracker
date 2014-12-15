package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;
import buhtig.steve.mergetracker.providers.MergeTrackerDataProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service to provide the data in REST format of various merge tracking reports.
 * Created by Steve on 30/11/2014.
 */
@Configurable
@RestController( )
public class BranchInfoReportServices {

    TreeMap<Long, BranchMergeTracker> reports;

    MergeTrackerDataProviderFactory factory;

    @Autowired
    public BranchInfoReportServices(final MergeTrackerDataProviderFactory factory) {
        this.factory = factory;
    }

    @PostConstruct
    public void initialise() {
        reports = factory.getProvider().getData();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/info/list")
    public Map<Long, String> list() {
        Map<Long, String> result = new TreeMap<>();
        for(BranchMergeTracker tracker : reports.values()) {
            result.put(tracker.getId()+1, tracker.getBranch().getBranchName());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/info/{branchName}")
    public List<Revision> infoByBranch (@PathVariable String branchName) {
        Branch branch = null;
        for(BranchMergeTracker tracker : reports.values()) {
            if (tracker.getBranch().getBranchName().contains(branchName)) {
                branch = tracker.getBranch();
                factory.getProvider().refresh(tracker);
                break;
            }
        }
        if (branch == null) {
            return new ArrayList<>();
        }
        return branch.getRevisions();
    }



}