package buhtig.steve.mergetracker.providers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class ApplicationConfigurationProviderTest {

    ApplicationConfigurationProvider provider;

    @Before
    public void setUp() throws Exception {
        this.provider = new ApplicationConfigurationProvider("testConfig.xml");
    }

    @Test
    public void testGetConfigProperties() throws Exception {
        assertThat(this.provider.getApplicationList(), containsInAnyOrder("Test1", "Test2", "Test3"));
    }

    @Test
    public void testGetConfigPropertiesApplicationTest1() throws Exception {
        assertThat((String)this.provider.getUrl("Test1"), equalTo("http://localhost:8080/repos/Test1"));
        final List<String> configProperties = this.provider.getMergeList("Test1");

        assertThat(configProperties, containsInAnyOrder("A|B", "A|C", "A|D", "D|A"));


        assertThat((String)this.provider.getType("Test1"), equalTo("subversion"));
    }

    @Test
    public void testGetConfigPropertiesApplicationTest2() throws Exception {
        assertThat((String)this.provider.getUrl("Test2"), nullValue());
        final List<String> configProperties = this.provider.getMergeList("Test2");


        assertThat(configProperties, containsInAnyOrder("A|B"));
        assertThat((String)this.provider.getType("Test2"), equalTo("test"));
    }

    @Test
    public void testGetConfigPropertiesApplicationTest3() throws Exception {
        assertThat((String)this.provider.getUrl("Test3"), equalTo("http://localhost:8080/repos/Test3"));
        final List<String> configProperties = this.provider.getMergeList("Test3");

        assertThat(configProperties, emptyCollectionOf(String.class));

        assertThat((String)this.provider.getType("Test3"), equalTo("subversion"));
    }

}