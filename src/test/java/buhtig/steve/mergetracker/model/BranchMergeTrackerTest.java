package buhtig.steve.mergetracker.model;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;

public class BranchMergeTrackerTest {

    BranchMergeTracker tracker;
    private Branch branch;
    private Branch mergeFrom;

    @Before
    public void setUp() throws Exception {
        branch = EasyMock.createMock("mockBranch", Branch.class);
        mergeFrom = EasyMock.createMock("mockMergeFrom", Branch.class);
        tracker = new BranchMergeTracker(branch, mergeFrom);
    }

    @Test
    public void testGetBranch() throws Exception {
       assertThat("get branch back", tracker.getBranch(), Matchers.sameInstance(branch));
    }

    @Test
    public void testGetMergeFrom() throws Exception {
        assertThat("get mergeFrom back", tracker.getMergeFrom(), Matchers.sameInstance(mergeFrom));
    }

    @Test
    public void testGetRevisionsToMergeIsEmpty() throws Exception {
        assertThat("get mergeFrom back", tracker.getRevisionsToMerge(), Matchers.empty());
    }

    @Test
    public void testAddRevision() throws Exception {
        Revision rev = EasyMock.createMock("mockRev", Revision.class);
        expect(rev.getRevision()).andReturn(123L);
        replay(rev);
        expect(mergeFrom.getRevision(123L)).andReturn(rev);
        replay(mergeFrom);
        tracker.addRevision(123L);
        verify(mergeFrom);
    }

}