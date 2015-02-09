package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.model.Revision;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import groovy.lang.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * This provides some test data for early development
 * Created by Steve on 30/11/2014.
 */
@Component
public class TestDataProvider implements IMergeTrackerDataProvider {

    private long bugTrackNumber = 12345L;
    private long revisions = 21;
    private IMessageParser parser;

    @Autowired
    public TestDataProvider(IMessageParser parser) {
        this.parser = parser;
    }

    @Override
    public void refresh(Repository repository) {
        for (Branch branch : repository.getBranches()) {
            if (!"trunk".equals(branch.getBranchName())) {
                getInfoForBranch(branch, repository, false);
            }
        }
        getInfoForBranch(repository.getBranch("trunk"), repository, true);

        for (BranchMergeTracker mergeTracker : repository.getMerges()) {
            getInfoOutstandingMerges(mergeTracker, repository.getUrl());
        }
    }

    private void getInfoOutstandingMerges(BranchMergeTracker mergeTracker, String url) {
        Branch branch = mergeTracker.getMergeFrom();
        if (!"trunk".equals(branch.getBranchName())) {
            int i = 1;
            for(Revision rev : branch.getRevisions()) {
                if (i>5 && i < 9) {
                   mergeTracker.addRevision(rev.getRevision());
                }
                i++;
            }
        }

    }

    private void getInfoForBranch(Branch branch, Repository repository, boolean trunkBranch) {
        if (trunkBranch) {
            //Add 20 revisions to trunk
            for(long rev =1; rev <= 20; rev++) {
                Revision revision = new Revision(rev, "Steve", "Messages 1", new Date());
                addBugNumber(revision);
                branch.addRevision(revision);
            }
            long stop = revisions + 20;
            for(long rev =revisions; rev <= stop; rev++) {
                Revision revision = new Revision(rev, "Steve", "Messages 2", new Date());
                addBugNumber(revision);
                branch.addRevision(revision);
                revisions++;
            }
        }
        else {
            long stop = revisions + 20;
            for(long rev = revisions; rev <= stop; rev++) {
                Revision revision = new Revision(rev, "Steve", "Messages on " + branch.getBranchName(), new Date());
                addBugNumber(revision);
                branch.addRevision(revision);
                revisions++;
            }
        }
    }


    private void addBugNumber(final Revision revision) {
        if (null != parser) {
            revision.setBugTrackId(parser.getBugIdFromMessage(revision.getMessage()));
        }
    }

}

