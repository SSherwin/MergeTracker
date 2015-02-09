package buhtig.steve.mergetracker.model;

import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RepositoryTest {

    Repository repository;

    @Before
    public void setUp() {
        repository = new Repository();
    }

    @Test
    public void testGetSetBranches() throws Exception {
        assertThat(repository.getBranches(), hasSize(0));
        final Branch test1 = new Branch("Test1");
        repository.addBranch(test1);
        assertThat(repository.getBranches(), contains(test1));

        final Branch test2 = new Branch("Test2");
        repository.addBranch(test2);
        assertThat(repository.getBranches(), contains(test1, test2));

        repository.removeBranch(test1);
        assertThat(repository.getBranches(), contains(test2));
    }

    @Test
    public void testGetMerges() throws Exception {
        final Branch branchFrom = new Branch("Test1");
        final Branch branchTo = new Branch("Test2");
        final Branch branchTo2 = new Branch("Test3");

        assertThat(repository.getBranches(), hasSize(0));
        final BranchMergeTracker test1 = new BranchMergeTracker(branchFrom,branchTo);
        repository.addMerge(test1);
        assertThat(repository.getMerges(), contains(test1));
        final BranchMergeTracker test2 = new BranchMergeTracker(branchFrom,branchTo2);
        repository.addMerge(test2);
        assertThat(repository.getMerges(), contains(test1, test2));
        repository.removeMerge(test1);
        assertThat(repository.getMerges(), contains(test2));

    }

    @Test
    public void testGetRevisions() throws Exception {
        assertThat(repository.getRevisions(), nullValue());
        List<Revision> testRevs = new ArrayList<>();
        repository.setRevisions(testRevs);
        assertSame(repository.getRevisions(), testRevs);

    }


    @Test
    public void testGetName() throws Exception {
        assertThat(repository.getName(), nullValue());
        repository.setName("Test1");
        assertThat(repository.getName(), equalTo("Test1"));

    }

    @Test
    public void testGetSetProvider() {
        assertThat(repository.getProvider(), nullValue());

        IMergeTrackerDataProvider mockProv = EasyMock.createMock("mockProvider", IMergeTrackerDataProvider.class);
        repository.setProvider(mockProv);
        assertSame(repository.getProvider(), mockProv);
    }

    @Test
    public void testAddBranchDuplicate() throws Exception {
        assertThat(repository.getBranches(), hasSize(0));
        final Branch test1 = new Branch("Test1");
        repository.addBranch(test1);
        assertThat(repository.getBranches(), contains(test1));
        repository.addBranch(test1);
        assertThat(repository.getBranches(), contains(test1));
    }

    @Test
    public void testAddMergeDuplicate() throws Exception {
        final Branch branchFrom = new Branch("Test1");
        final Branch branchTo = new Branch("Test1");

        assertThat(repository.getBranches(), hasSize(0));
        final BranchMergeTracker test1 = new BranchMergeTracker(branchFrom,branchTo);
        repository.addMerge(test1);
        assertThat(repository.getMerges(), contains(test1));
        final BranchMergeTracker test2 = new BranchMergeTracker(branchFrom,branchTo);
        repository.addMerge(test2);
        assertThat(repository.getMerges(), contains(test1));

    }
}