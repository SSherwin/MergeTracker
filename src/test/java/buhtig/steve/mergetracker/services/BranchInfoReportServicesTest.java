package buhtig.steve.mergetracker.services;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import buhtig.steve.mergetracker.providers.MergeTrackerDataProviderFactory;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;


public class BranchInfoReportServicesTest  {

    @Test
    public void testListIs0Length() {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker> testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);
        
        service.initialise();

        final Map<Long, String> list = service.list();
        assertThat("list is 0 long", list.size(), equalTo(0));

    }

    @Test
    public void testListIs3Length() {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker>testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);
        
        // Add Test data
        BranchMergeTracker mock1 = EasyMock.createMock("mock1", BranchMergeTracker.class);
        BranchMergeTracker mock2 = EasyMock.createMock("mock2", BranchMergeTracker.class);
        BranchMergeTracker mock3 = EasyMock.createMock("mock3", BranchMergeTracker.class);
        expect(mock1.getId()).andReturn(0L);
        expect(mock2.getId()).andReturn(1L);
        expect(mock3.getId()).andReturn(2L);
        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
        expect(mock1.getBranch()).andReturn(mockBranch1);
        expect(mock2.getBranch()).andReturn(mockBranch2);
        expect(mock3.getBranch()).andReturn(mockBranch3);
        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");


        replay(mock1);
        replay(mock2);
        replay(mock3);
        replay(mockBranch1);
        replay(mockBranch2);
        replay(mockBranch3);

        testData.put(0L, mock1);
        testData.put(1L, mock2);
        testData.put(2L, mock3);

        service.initialise();

        final Map<Long, String> list = service.list();
        assertThat("list is 3 long", list.size(), equalTo(3));
        assertThat("list has mock 1 branch", list.get(1L), equalTo("mockBranch1"));
        assertThat("list has mock 2 branch", list.get(2L), equalTo("mockBranch2"));
        assertThat("list has mock 3 branch", list.get(3L), equalTo("mockBranch3"));

        verify(mock1);
        verify(mock2);
        verify(mock3);

    }

    @Test
    public void testInfoByBranchNoData() throws Exception {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker>testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);
        
        service.initialise();
        final List<Revision> list = service.infoByBranch("mockBranch1");
        assertThat("list is 0 long", list.size(), equalTo(0));
    }

    @Test
    public void testInfoByBranchWithDataFound() throws Exception {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker>testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData).anyTimes();

        
        // Add Test data
        BranchMergeTracker mock1 = EasyMock.createMock("mock1", BranchMergeTracker.class);
        BranchMergeTracker mock2 = EasyMock.createMock("mock2", BranchMergeTracker.class);
        BranchMergeTracker mock3 = EasyMock.createMock("mock3", BranchMergeTracker.class);
        expect(mock1.getId()).andReturn(0L);
        expect(mock2.getId()).andReturn(1L);
        expect(mock3.getId()).andReturn(2L);
        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
        expect(mock1.getBranch()).andReturn(mockBranch1).anyTimes();
        expect(mock2.getBranch()).andReturn(mockBranch2).anyTimes();
        expect(mock3.getBranch()).andReturn(mockBranch3).anyTimes();
        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");

        mockProvider.refresh(mock1); //EXPECT THIS
        List<Revision> revList = new ArrayList<>();
        expect(mockBranch1.getRevisions()).andReturn(revList);
        replay(mock1);
        replay(mock2);
        replay(mock3);
        replay(mockBranch1);
        replay(mockBranch2);
        replay(mockBranch3);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);

        testData.put(0L, mock1);
        testData.put(1L, mock2);
        testData.put(2L, mock3);

        service.initialise();
        final List<Revision> list = service.infoByBranch("mockBranch1");
        assertThat("list is returned", list, sameInstance(revList));
    }

    @Test
    public void testInfoByBranchWithDataFoundLastEntry() throws Exception {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker>testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData).anyTimes();


        // Add Test data
        BranchMergeTracker mock1 = EasyMock.createMock("mock1", BranchMergeTracker.class);
        BranchMergeTracker mock2 = EasyMock.createMock("mock2", BranchMergeTracker.class);
        BranchMergeTracker mock3 = EasyMock.createMock("mock3", BranchMergeTracker.class);
        expect(mock1.getId()).andReturn(0L);
        expect(mock2.getId()).andReturn(1L);
        expect(mock3.getId()).andReturn(2L);
        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
        expect(mock1.getBranch()).andReturn(mockBranch1).anyTimes();
        expect(mock2.getBranch()).andReturn(mockBranch2).anyTimes();
        expect(mock3.getBranch()).andReturn(mockBranch3).anyTimes();
        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");

        mockProvider.refresh(mock3); //EXPECT THIS
        List<Revision> revList = new ArrayList<>();
        expect(mockBranch3.getRevisions()).andReturn(revList);
        replay(mock1);
        replay(mock2);
        replay(mock3);
        replay(mockBranch1);
        replay(mockBranch2);
        replay(mockBranch3);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);

        testData.put(0L, mock1);
        testData.put(1L, mock2);
        testData.put(2L, mock3);

        service.initialise();
        final List<Revision> list = service.infoByBranch("mockBranch3");
        assertThat("list is returned", list, sameInstance(revList));
    }

    @Test
    public void testInfoByBranchWithDataBranchNotFound() throws Exception {
        final MergeTrackerDataProviderFactory mergeTrackerDataProviderFactory = EasyMock.createMock("mergeTrackerDataProviderFactory", MergeTrackerDataProviderFactory.class);
        BranchInfoReportServices service = new BranchInfoReportServices(mergeTrackerDataProviderFactory);

        IMergeTrackerDataProvider mockProvider = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);

        expect(mergeTrackerDataProviderFactory.getProvider()).andReturn(mockProvider).anyTimes();
        final TreeMap<Long, BranchMergeTracker>testData = new TreeMap<>();


        expect(mockProvider.getData()).andReturn(testData).anyTimes();


        // Add Test data
        BranchMergeTracker mock1 = EasyMock.createMock("mock1", BranchMergeTracker.class);
        BranchMergeTracker mock2 = EasyMock.createMock("mock2", BranchMergeTracker.class);
        BranchMergeTracker mock3 = EasyMock.createMock("mock3", BranchMergeTracker.class);
        expect(mock1.getId()).andReturn(0L);
        expect(mock2.getId()).andReturn(1L);
        expect(mock3.getId()).andReturn(2L);
        Branch mockBranch1 =  EasyMock.createMock("mockBranch1", Branch.class);
        Branch mockBranch2 =  EasyMock.createMock("mockBranch2", Branch.class);
        Branch mockBranch3 =  EasyMock.createMock("mockBranch3", Branch.class);
        expect(mock1.getBranch()).andReturn(mockBranch1).anyTimes();
        expect(mock2.getBranch()).andReturn(mockBranch2).anyTimes();
        expect(mock3.getBranch()).andReturn(mockBranch3).anyTimes();
        expect(mockBranch1.getBranchName()).andReturn("mockBranch1");
        expect(mockBranch2.getBranchName()).andReturn("mockBranch2");
        expect(mockBranch3.getBranchName()).andReturn("mockBranch3");

        mockProvider.refresh(mock3); //EXPECT THIS
        List<Revision> revList = new ArrayList<>();
        expect(mockBranch3.getRevisions()).andReturn(revList);
        replay(mock1);
        replay(mock2);
        replay(mock3);
        replay(mockBranch1);
        replay(mockBranch2);
        replay(mockBranch3);

        replay(mockProvider);
        replay(mergeTrackerDataProviderFactory);

        testData.put(0L, mock1);
        testData.put(1L, mock2);
        testData.put(2L, mock3);

        service.initialise();
        final List<Revision> list = service.infoByBranch("branchNotInMap");
        assertThat("list is returned", list, not(sameInstance(revList)));
        assertThat("list is 0 length", list, hasSize(0));
    }
}