package buhtig.steve.mergetracker.model;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


import java.util.Date;

public class RevisionTest {

    private Revision rev;
    private Date dateForTest;

    @Before
    public void setUp() throws Exception {
        dateForTest = new Date();
        rev = new Revision(123L,"Steve", "Test message without issue", dateForTest);
    }


    @Test
    public void testRevisionCreated() throws Exception {
        assertThat("Values set correctly for rev", rev.getRevision(), equalTo(123L));
        assertThat("Values set correctly for author", rev.getAuthor(), equalTo("Steve"));
        assertThat("Values set correctly for msg", rev.getMessage(), equalTo("Test message without issue"));
        assertThat("Values set correctly for date", rev.getCommitDate(), equalTo(dateForTest));
    }

}