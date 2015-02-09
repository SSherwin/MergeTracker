package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.manager.MergeTrackerManager;
import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;


public class BranchInfoReportServicesTest  {

    @Test
    public void testListIs1Length() {
        final MergeTrackerManager mergeTrackerManager = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerManager.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerManager);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
        Repository mockRepos = EasyMock.createMock("mockRepos", Repository.class);


        expect(mergeTrackerManager.getRepository("Test1")).andReturn(mockRepos).anyTimes();
        final TreeMap<String, Branch> testData = new TreeMap<>();


        List<Branch> branchesList = new ArrayList<>();
        branchesList.add(new Branch(Branch.TRUNK));
        expect(mockRepos.getBranches()).andReturn(branchesList);

        replay(mockProvider);
        replay(mergeTrackerManager);
        replay(mockRepos);

        final Map<Long, String> list = service.list("Test1");
        assertThat("list is 1 long", list.size(), equalTo(1));

    }

//    @Test
//    public void testListIs3Length() {
//        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
//        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);
//
//        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
//
//        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
//        final TreeMap<String, Branch>testData = new TreeMap<>();
//
//        expect(mockProvider.getBranchData()).andReturn(testData).anyTimes();
//
//
//        // Add Test data
//
//        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
//        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
//        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
//
//        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
//        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
//        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");
//
//        List<Revision> revList = new ArrayList<>();
//        expect(mockBranch3.getRevisions()).andReturn(revList);
//
//        replay(mockBranch1);
//        replay(mockBranch2);
//        replay(mockBranch3);
//
//        replay(mockProvider);
//        replay(mergeTrackerDataProviderFactory);
//
//        testData.put("mockBranch1", mockBranch1);
//        testData.put("mockBranch2", mockBranch2);
//        testData.put("mockBranch3", mockBranch3);
//
//        service.initialise();
//
//        final Map<Long, String> list = service.list();
//        assertThat("list is 3 long", list.size(), equalTo(3));
//        assertThat("list has mock 1 branch", list.get(1L), equalTo("mockBranch1"));
//        assertThat("list has mock 2 branch", list.get(2L), equalTo("mockBranch2"));
//        assertThat("list has mock 3 branch", list.get(3L), equalTo("mockBranch3"));
//
//
//    }
//
//    @Test
//    public void testInfoByBranchNoData() throws Exception {
//        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
//        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);
//
//        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
//
//        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
//        final TreeMap<String, Branch>testData = new TreeMap<>();
//
//
//        expect(mockProvider.getBranchData()).andReturn(testData);
//
//        replay(mockProvider);
//        replay(mergeTrackerDataProviderFactory);
//
//        service.initialise();
//        final List<Revision> list = service.infoByBranch("mockBranch1");
//        assertThat("list is 0 long", list.size(), equalTo(0));
//    }
//
//    @Test
//    public void testInfoByBranchWithDataFound() throws Exception {
//        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
//        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);
//
//        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
//
//        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
//        final TreeMap<String, Branch>testData = new TreeMap<>();
//
//        expect(mockProvider.getBranchData()).andReturn(testData).anyTimes();
//
//
//        // Add Test data
//
//        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
//        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
//        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
//
//        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
//        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
//        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");
//
//        List<Revision> revList = new ArrayList<>();
//        expect(mockBranch1.getRevisions()).andReturn(revList);
//
//        replay(mockBranch1);
//        replay(mockBranch2);
//        replay(mockBranch3);
//
//        replay(mockProvider);
//        replay(mergeTrackerDataProviderFactory);
//
//        testData.put("mockBranch1", mockBranch1);
//        testData.put("mockBranch2", mockBranch2);
//        testData.put("mockBranch3", mockBranch3);
//
//        service.initialise();
//        final List<Revision> list = service.infoByBranch("mockBranch1");
//        assertThat("list is returned", list, sameInstance(revList));
//    }
//
//    @Test
//    public void testInfoByBranchWithDataFoundLastEntry() throws Exception {
//        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
//        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);
//
//        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
//
//        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
//        final TreeMap<String, Branch>testData = new TreeMap<>();
//
//        expect(mockProvider.getBranchData()).andReturn(testData).anyTimes();
//
//
//        // Add Test data
//
//        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
//        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
//        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
//
//        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
//        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
//        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");
//
//        List<Revision> revList = new ArrayList<>();
//        expect(mockBranch3.getRevisions()).andReturn(revList);
//
//        replay(mockBranch1);
//        replay(mockBranch2);
//        replay(mockBranch3);
//
//        replay(mockProvider);
//        replay(mergeTrackerDataProviderFactory);
//
//        testData.put("mockBranch1", mockBranch1);
//        testData.put("mockBranch2", mockBranch2);
//        testData.put("mockBranch3", mockBranch3);
//
//        service.initialise();
//        final List<Revision> list = service.infoByBranch("mockBranch3");
//        assertThat("list is returned", list, sameInstance(revList));
//    }
//
//    @Test
//    public void testInfoByBranchWithDataBranchNotFound() throws Exception {
//        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
//        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);
//
//        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
//
//        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
//        final TreeMap<String, Branch>testData = new TreeMap<>();
//
//        expect(mockProvider.getBranchData()).andReturn(testData).anyTimes();
//
//
//        // Add Test data
//
//        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
//        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
//        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
//
//        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
//        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
//        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");
//
//        List<Revision> revList = new ArrayList<>();
//        expect(mockBranch3.getRevisions()).andReturn(revList);
//
//        replay(mockBranch1);
//        replay(mockBranch2);
//        replay(mockBranch3);
//
//        replay(mockProvider);
//        replay(mergeTrackerDataProviderFactory);
//
//        testData.put("mockBranch1", mockBranch1);
//        testData.put("mockBranch2", mockBranch2);
//        testData.put("mockBranch3", mockBranch3);
//
//        service.initialise();
//        final List<Revision> list = service.infoByBranch("branchNotInMap");
//        assertThat("list is returned", list, not(sameInstance(revList)));
//        assertThat("list is 0 length", list, hasSize(0));
//    }
}