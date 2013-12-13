package uk.ac.bcu.kbe.bioen.service;

import org.junit.Before;
import org.junit.Test;
import uk.ac.bcu.kbe.bioen.BaseTest;
import uk.ac.bcu.kbe.bioen.dao.ExcelReader;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ID118320
 * Date: 22/10/13
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class BiogasManagerTest extends BaseTest {
    BiogasManager manager;

    @Before
    public void initialize() throws IOException {
        manager = new BiogasManager(new ExcelReader(excelFilename),variableCellMappings);
    }

    @Test
    public void testGetOutput() throws Exception {
       manager.setInput("{\"cows\":\"200\",\"area\":\"300\"}");
       String output = manager.getOutput();
       assertEquals("{\"area\":300.0,\"biogas\":20.0,\"energy\":100.0,\"cows\":200.0}", output);

    }


}
