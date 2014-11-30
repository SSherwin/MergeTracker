package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Steve on 30/11/2014.
 */
@Component
public class TestDataProvider implements IMergeTrackerDataProvider {


    @Override
    public TreeMap<Long, BranchMergeTracker> loadData() {
        final Branch release1 = new Branch("branches/RELEASE-1.0.0");
        final Branch release2 = new Branch("branches/RELEASE-2.0.0");
        final Branch trunk = new Branch("trunk");
        final Branch feature = new Branch("branches/FEATURE1");

        BranchMergeTracker merge1 = new BranchMergeTracker(release2, release1);
        BranchMergeTracker merge2 = new BranchMergeTracker(feature, trunk);
        BranchMergeTracker merge3 = new BranchMergeTracker(trunk, feature);
        BranchMergeTracker merge4 = new BranchMergeTracker(trunk, release2);


        //Add 20 revisions to trunk
        for(long rev =1; rev <= 20; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 1", new Date());
            trunk.addRevision(revision);
        }

        for(long rev =21; rev <= 25; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 2", new Date());
            release1.addRevision(revision);
        }

        for(long rev =26; rev <= 30; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 3", new Date());
            trunk.addRevision(revision);
        }

        for(long rev =31; rev <= 35; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 4", new Date());
            release1.addRevision(revision);
        }

        for(long rev =36; rev <= 40; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 5", new Date());
            trunk.addRevision(revision);
        }

        for(long rev =41; rev <= 45; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            release2.addRevision(revision);
        }

        for(long rev =46; rev <= 60; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            trunk.addRevision(revision);
        }

        for(long rev =61; rev <= 80; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            if (rev%2 == 0) {
                trunk.addRevision(revision);
                merge2.addRevision(rev);
            } else {
                feature.addRevision(revision);
                merge3.addRevision(rev);
            }
        }


        merge1.addRevision(22L);
        merge1.addRevision(23L);
        merge1.addRevision(25L);

        TreeMap<Long, BranchMergeTracker> result = new TreeMap<>();
        result.put(1L, merge1);
        result.put(2L, merge2);
        result.put(3L, merge3);
        result.put(4L, merge4);


        return result;
    }

    @Override
    public void refresh(BranchMergeTracker branchMergeTracker) {

    }
}
