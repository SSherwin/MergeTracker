package buhtig.steve.mergetracker.manager;

import buhtig.steve.mergetracker.providers.ApplicationConfigurationProvider;
import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;
import buhtig.steve.mergetracker.providers.SubversionProvider;
import buhtig.steve.mergetracker.providers.TestDataProvider;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.hamcrest.Matchers;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MergeTrackerManagerTest extends TestCase {

    public static final String APPLICATION_1 = "Application1";
    public static final String APPLICATION_2 = "Application2";
    private MergeTrackerManager manager;
    private ApplicationConfigurationProvider mockConfig;

    @Before
    public void setUp() {
        this.mockConfig = EasyMock.createMock("mockConfig", ApplicationConfigurationProvider.class);
        this.manager = new MergeTrackerManager(mockConfig);
    }

    public void testRefreshNoApps() throws Exception {
        List<String> list = new ArrayList<>();
        expect(mockConfig.getApplicationList()).andReturn(list);
        replay(mockConfig);
        manager.refresh();
        assertThat(manager.getRepositories(), hasSize(0));
        verify(mockConfig);
    }


    public void testRefreshTwoApps() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(APPLICATION_1);
        list.add(APPLICATION_2);
        expect(mockConfig.getApplicationList()).andReturn(list);
        expect(mockConfig.getType(APPLICATION_1)).andReturn("subversion");
        expect(mockConfig.getUrl(APPLICATION_1)).andReturn("Http://localhost/repos/Application1/").anyTimes();
        expect(mockConfig.getType(APPLICATION_2)).andReturn("testdata");
        expect(mockConfig.getUrl(APPLICATION_2)).andReturn("Http://localhost/repos/Application2/").anyTimes();

        List<String> mergeList1 = new ArrayList<>();
        mergeList1.add("branch/REL-1.0|branch/REL-2.0");
        mergeList1.add("branch/REL-2.0|branch/REL-3.0");
        mergeList1.add("branch/FEAT-12345|trunk");
        mergeList1.add("trunk|branch/FEAT-12345");

        expect(mockConfig.getMergeList(APPLICATION_1)).andReturn(mergeList1);

        List<String> mergeList2 = new ArrayList<>();
        expect(mockConfig.getMergeList(APPLICATION_2)).andReturn(mergeList2);
        replay(mockConfig);
        manager.refresh();
        assertThat(manager.getRepositories(), hasSize(2));
        assertThat(manager.getRepository(APPLICATION_1).getProvider(), instanceOf(SubversionProvider.class));
        assertThat(manager.getRepository(APPLICATION_2).getProvider(), instanceOf(TestDataProvider.class));

        assertThat(manager.getRepository(APPLICATION_1).getBranches(), hasSize(5));
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-1.0"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-2.0"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-3.0"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("trunk"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/FEAT-12345"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getMerges(), hasSize(4));
        verify(mockConfig);
    }

    public void testRefreshTwoAppsDoubleRefreshRemove() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(APPLICATION_1);
        list.add(APPLICATION_2);
        expect(mockConfig.getApplicationList()).andReturn(list).times(2);
        expect(mockConfig.getType(APPLICATION_1)).andReturn("subversion");
        expect(mockConfig.getUrl(APPLICATION_1)).andReturn("Http://localhost/repos/Application1/").anyTimes();
        expect(mockConfig.getType(APPLICATION_2)).andReturn("testdata");
        expect(mockConfig.getUrl(APPLICATION_2)).andReturn("Http://localhost/repos/Application2/").anyTimes();

        List<String> mergeList1 = new ArrayList<>();
        mergeList1.add("branch/REL-1.0|branch/REL-2.0");
        mergeList1.add("branch/REL-2.0|branch/REL-3.0");
        mergeList1.add("branch/FEAT-12345|trunk");
        mergeList1.add("trunk|branch/FEAT-12345");

        expect(mockConfig.getMergeList(APPLICATION_1)).andReturn(mergeList1);
        List<String> mergeList2 = new ArrayList<>();
        mergeList2.add("branch/REL-1.0|branch/REL-2.0");
        mergeList2.add("branch/FEAT-12345|trunk");
        mergeList2.add("trunk|branch/FEAT-12345");

        expect(mockConfig.getMergeList(APPLICATION_1)).andReturn(mergeList2);

        List<String> mergeList3 = new ArrayList<>();
        expect(mockConfig.getMergeList(APPLICATION_2)).andReturn(mergeList3).times(2);
        replay(mockConfig);
        manager.refresh();
        manager.refresh();
        assertThat(manager.getRepositories(), hasSize(2));
        assertThat(manager.getRepository(APPLICATION_1).getProvider(), instanceOf(SubversionProvider.class));
        assertThat(manager.getRepository(APPLICATION_2).getProvider(), instanceOf(TestDataProvider.class));

        assertThat(manager.getRepository(APPLICATION_1).getBranches(), hasSize(4));
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-1.0"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-2.0"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/REL-3.0"), nullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("trunk"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getBranch("branch/FEAT-12345"), notNullValue());
        assertThat(manager.getRepository(APPLICATION_1).getMerges(), hasSize(3));
        verify(mockConfig);
    }
}