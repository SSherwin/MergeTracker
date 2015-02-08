package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.manager.MergeTrackerManager;
import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.model.Revision;
import buhtig.steve.mergetracker.providers.MergeTrackerDataProviderFactory;
import buhtig.steve.mergetracker.reportdata.ReportByRevision;
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

    private MergeTrackerManager mergeTrackManager;

    @Autowired
    public BranchInfoReportServices(final MergeTrackerManager mergeTrackManager) {
        this.mergeTrackManager = mergeTrackManager;
    }
    @RequestMapping(method = RequestMethod.GET, value ="/info/{applicationName}/list")
    public Map<Long, String> list(@PathVariable String applicationName) {
        Map<Long, String> result = new TreeMap<>();
        final Repository repository = mergeTrackManager.getRepository(applicationName);
        Long id = 0L;
        for(Branch branch : repository.getBranches()) {
            result.put(++id, branch.getBranchName());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/info/{applicationName}/{branchName}")
    public List<Revision> infoByBranch (@PathVariable String applicationName, @PathVariable String branchName) {
        final Repository repository = mergeTrackManager.getRepository(applicationName);
        Branch branch = repository.getBranch(branchName);
        if (null == branch) {
            return new ArrayList<>();
        }
        return branch.getRevisions();
    }



}