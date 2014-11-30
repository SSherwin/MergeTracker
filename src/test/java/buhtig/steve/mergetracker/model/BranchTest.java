package buhtig.steve.mergetracker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BranchTest {

    final Revision revision123 = new Revision(123L, "Steve", "Msg123", new Date());
    final Revision revision1 = new Revision(1L, "Steve", "Msg1", new Date());
    final Revision revision12 = new Revision(12L, "Steve", "Msg12", new Date());
    final Revision revision500 = new Revision(500L, "Steve", "Msg500", new Date());
    private Branch branch;

    @Before
    public void setUp() throws Exception {
        branch = new Branch("TestBranch");
    }

    @Test
    public void testGetRevisions() throws Exception {
        addRevisions();
        assertThat("4 revision added", branch.getRevisions(), hasSize(4));
        assertThat("1 revision added", branch.getRevisions().get(0), equalTo(revision1));
        assertThat("12 revision added", branch.getRevisions().get(1), equalTo(revision12));
        assertThat("123 revision added", branch.getRevisions().get(2), equalTo(revision123));
        assertThat("500 revision added", branch.getRevisions().get(3), equalTo(revision500));

    }

    @Test
    public void testGetEarlistRevision() throws Exception {
        addRevisions();
        assertThat("1 should be the earliest", branch.getEarlistRevision(), equalTo(revision1));
    }

    @Test
    public void testGetEarlistRevisionNull() throws Exception {
        assertThat("1 should be the earliest", branch.getEarlistRevision(), nullValue());
    }

    @Test
    public void testGetLastRevision() throws Exception {
        addRevisions();
        assertThat("500 should be the latest", branch.getLastRevision(), equalTo(revision500));
    }

    @Test
    public void testGetLastRevisionNull() throws Exception {
        assertThat("500 should be the latest", branch.getLastRevision(), nullValue());

    }

    @Test
    public void testAddRevision() throws Exception {
        final Revision revision = new Revision(123L, "Steve", "Msg", new Date());
        branch.addRevision(revision);
        assertThat("1 revision added", branch.getRevisions().get(0), equalTo(revision));
    }

    @Test
    public void testGetBranchName() throws Exception {
        assertThat("Branch name set when constructed", branch.getBranchName(), equalTo("TestBranch"));
    }

    @Test
    public void getRevisionReturningAnswer() {
        addRevisions();
        assertThat("we can get revison 12", branch.getRevision(12L), equalTo(revision12));
    }

    @Test
    public void getRevisionReturningNull() {
        assertThat("we can get revison 21", branch.getRevision(21L), nullValue());
    }

    private void addRevisions() {
        branch.addRevision(revision123);
        branch.addRevision(revision1);
        branch.addRevision(revision12);
        branch.addRevision(revision500);
    }
}