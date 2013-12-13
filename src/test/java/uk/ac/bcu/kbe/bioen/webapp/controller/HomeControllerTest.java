package uk.ac.bcu.kbe.bioen.webapp.controller;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ID118320
 * Date: 23/10/13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class HomeControllerTest {

    @Test
    public void testGetBiogasJson() throws Exception {
       HomeController controller = new HomeController();
        String output = controller.getBiogasJson("{\"zeroGrazedCows\":\"20\",\"summerGrazedCows\":\"20\"}");
        System.out.println(output);
        assertTrue(output.contains("\"zeroGrazedCows\":20"));
        assertTrue(output.contains("\"summerGrazedCows\":20"));
    }
}
