package buhtig.steve.mergetracker.controllers;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HomeControllerTest {

    private HomeController controller;

    @Before
    public void setUp() {
        controller = new HomeController();
    }

    @Test
    public void testIndex() throws Exception {
        assertThat("returns index", controller.index(), equalTo("index"));
    }
}