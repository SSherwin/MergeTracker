package buhtig.steve.mergetracker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class RepositoryTest {

    Repository repository;

    @Before
    public void setUp() {
        repository = new Repository();
    }

    @Test
    public void testGetSetBranches() throws Exception {
        assertThat(repository.getBranches(), nullValue());
        List<Branch> testBranches = new ArrayList<>();
        repository.setBranches(testBranches);
        assertSame(repository.getBranches(), testBranches);
    }

    @Test
    public void testGetMerges() throws Exception {
        assertThat(repository.getMerges(), nullValue());
        List<BranchMergeTracker> testMerges = new ArrayList<>();
        repository.setMerges(testMerges);
        assertSame(repository.getMerges(), testMerges);

    }

    @Test
    public void testGetRevisions() throws Exception {
        assertThat(repository.getRevisions(), nullValue());
        List<Revision> testRevs = new ArrayList<>();
        repository.setRevisions(testRevs);
        assertSame(repository.getRevisions(), testRevs);

    }

}