package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
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
public class TestDataProvider implements IMergeTrackerDataProvider {

    private long bugTrackNumber = 12345L;

    @Value("${revisiondata.url}")
    private String url;

    @Autowired
    ApplicationConfigurationProvider configProv;


    @Autowired
    private IMessageParser parser;

    private TreeMap<Long, BranchMergeTracker> mergeData;
    private TreeMap<String, Branch> branchData;

    @PostConstruct
    public void loadData() {
        branchData = new TreeMap<>();

        final Branch release1 = new Branch("branches/RELEASE-1.0.0");
        final Branch release2 = new Branch("branches/RELEASE-2.0.0");
        final Branch trunk = new Branch("trunk");
        final Branch feature = new Branch("branches/FEATURE1");

        branchData.put("RELEASE-1.0.0", release1);
        branchData.put("RELEASE-2.0.0", release2);
        branchData.put("trunk", trunk);
        branchData.put("FEATURE1", feature);


        BranchMergeTracker merge1 = new BranchMergeTracker(release2, release1);
        BranchMergeTracker merge2 = new BranchMergeTracker(feature, trunk);
        BranchMergeTracker merge3 = new BranchMergeTracker(trunk, feature);
        BranchMergeTracker merge4 = new BranchMergeTracker(trunk, release2);


        //Add 20 revisions to trunk
        for(long rev =1; rev <= 20; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 1", new Date());
            addBugNumber(revision);
            trunk.addRevision(revision);
        }

        for(long rev =21; rev <= 25; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 2", new Date());
            addBugNumber(revision);
            release1.addRevision(revision);
        }

        for(long rev =26; rev <= 30; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 3", new Date());
            addBugNumber(revision);
            trunk.addRevision(revision);
        }

        for(long rev =31; rev <= 35; rev++) {
            Revision revision = new Revision(rev, "Steve", "Messages 4", new Date());
            addBugNumber(revision);
            release1.addRevision(revision);
        }

        for(long rev =36; rev <= 40; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 5", new Date());
            addBugNumber(revision);
            trunk.addRevision(revision);
        }

        for(long rev =41; rev <= 45; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            addBugNumber(revision);
            release2.addRevision(revision);
        }

        for(long rev =46; rev <= 60; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            addBugNumber(revision);
            trunk.addRevision(revision);
        }

        for(long rev =61; rev <= 80; rev++) {
            Revision revision = new Revision(rev, "Bob", "Messages 6", new Date());
            addBugNumber(revision);
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


        mergeData = result;
    }

    @Override
    public void refresh(BranchMergeTracker branchMergeTracker) {
      //  String value = (String)this.configProv.getConfigProperties("Test1", "url");
     //   System.out.println("Value = " + value);


      //  List<Object> values = (List)this.configProv.getConfigProperties("Test1", "merges.merge");
      //  System.out.println("Values = " + values.size());
    }

    @Override
    public TreeMap<Long, BranchMergeTracker> getMergeData() {
        return mergeData;
    }

    @Override
    public TreeMap<String, Branch> getBranchData() {
        return branchData;
    }

    private void addBugNumber(Revision revision) {
        revision.setBugTrackId(parser.getBugIdFromMessage(revision.getMessage()));
    }

}

