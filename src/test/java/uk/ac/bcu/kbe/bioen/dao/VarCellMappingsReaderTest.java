package uk.ac.bcu.kbe.bioen.dao;

import org.junit.Before;
import org.junit.Test;
import uk.ac.bcu.kbe.bioen.BaseTest;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ID118320
 * Date: 11/11/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class VarCellMappingsReaderTest extends BaseTest {
    VarCellMappingsReader varcellReader;

    @Before
    public void initialize() throws IOException {
        varcellReader = new VarCellMappingsReader(excelCellsFilename);
    }

    @Test
    public void testProcess() throws Exception {
        assertEquals(4,varcellReader.process(excelCellsFilename).size());
    }

    @Test
    public void testGetArrayValues() throws Exception {
       String[][] arrayValues = varcellReader.getArrayValues();
       assertEquals(4,arrayValues.length);
       assertEquals("cows",arrayValues[0][0]);
       assertEquals("input",arrayValues[0][4]);
       assertEquals("biogas",arrayValues[3][0]);
       assertEquals("output",arrayValues[3][4]);
    }
}
