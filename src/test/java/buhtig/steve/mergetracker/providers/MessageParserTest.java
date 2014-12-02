package buhtig.steve.mergetracker.providers;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class MessageParserTest {

    public static final long ISSUE_12345 = 12345L;
    IMessageParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new MessageParser();
    }

    @Test
    public void testGetBugIdFromMessageEmpty() throws Exception {
        final String msg = "";
        assertThat("empty message is null.", parser.getBugIdFromMessage(msg), nullValue());
    }

    @Test
    public void testGetBugIdFromMessageNoIssue() throws Exception {
        final String msg = "Message that is missing  the issue number";
        assertThat("no issue in message is null.", parser.getBugIdFromMessage(msg), nullValue());
    }


    @Test
    public void testGetBugIdFromMessageNoIssueNotNewLine() throws Exception {
        final String msg = "Message that is missing  the issue number issue #12345";
        assertThat("issue not on line but in message.", parser.getBugIdFromMessage(msg), nullValue());
    }

    @Test
    public void testGetBugIdFromMessageNewLineMissingHash() throws Exception {
        final String msg = "Message that is missing  the issue number\n issue 12345";
        assertThat("issue missing # in message.", parser.getBugIdFromMessage(msg), nullValue());
    }

    @Test
    public void testGetBugIdFromMessageInSingleLine() throws Exception {
        final String msg = "issue #12345";
        assertThat("issue in message on one line.", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
         public void testGetBugIdFromMessageInMultiNewLine() throws Exception {
        final String msg = "Testing\nSeveral\nLines\nissue #12345";
        assertThat("issue in message that is over many lines.", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
    public void testGetBugIdFromMessageInMultiReturnNewLine() throws Exception {
        final String msg = "Testing\r\nSeveral\r\nLines\r\nissue #12345";
        assertThat("issue in message return + newline", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
    public void testGetBugIdFromMessageNewLineAfterIssue() throws Exception {
        final String msg = "issue #12345\n";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }


    @Test
    public void testGetBugIdFromMessageSpaceNewLineAfterIssue() throws Exception {
        final String msg = "issue #12345  \n  \n";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
    public void testGetBugIdFromMessageNotCaseSensitive() throws Exception {
        final String msg = "Issue #12345  \n  \n";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
         public void testGetBugIdFromMessageManyIssue() throws Exception {
        final String msg = "Several isues \r\nIssus #23445\nissue #12345";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
    public void testGetBugIdFromMessageLeadingZeros() throws Exception {
        final String msg = "Several isues \r\nIssus #23445\nissue #0012345";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(ISSUE_12345));
    }

    @Test
    public void testGetBugIdFromMessageAllZeros() throws Exception {
        final String msg = "issue #0000000";
        assertThat("issue in message newline at end", parser.getBugIdFromMessage(msg), equalTo(0L));
    }
}