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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isA;
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
        expect(mockConfig.getType(APPLICATION_2)).andReturn("testdata");
        replay(mockConfig);
        manager.refresh();
        assertThat(manager.getRepositories(), hasSize(2));
        assertThat(manager.getRepository(APPLICATION_1).getProvider(), instanceOf(SubversionProvider.class));
        assertThat(manager.getRepository(APPLICATION_2).getProvider(), instanceOf(TestDataProvider.class));

        verify(mockConfig);
    }
}