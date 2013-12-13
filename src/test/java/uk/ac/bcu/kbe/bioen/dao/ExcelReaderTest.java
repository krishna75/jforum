package uk.ac.bcu.kbe.bioen.dao;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
* Created with IntelliJ IDEA.
* User: ID118320
* Date: 23/10/13
* Time: 10:41
* To change this template use File | Settings | File Templates.
*/
public class ExcelReaderTest {
    ExcelReader reader;

    @Before
    public void initialize() throws IOException {
        String filename = this.getClass().getClassLoader().getResource("test.xlsx").getFile();
        reader = new ExcelReader(filename);
    }


    /* Tests for getters */

    @Test
    public void testGetRowValues() throws Exception {
        assertTrue(contains(reader.getRowValues(0, 4)
                ,(Lists.newArrayList("Name", "Address", "Phone", "Email"))));
        assertTrue(contains(reader.getRowValues(0, 5)
                ,(Lists.newArrayList("Krishna Sapkota", "303 Hollow Way", "7.727682394E9", "ks_21285@hotmail.com"))));

    }

    @Test
    public void testGetCellValue() throws IOException {
        assertEquals("Name", reader.getCellValue(0, 5, 0));
        assertEquals("Krishna Sapkota", reader.getCellValue(0, 6, 0));
    }


    @Test
    public void testGetCellValueWithCellId() throws IOException {
        assertEquals("Name", reader.getCellValueString(0, "a5"));
        assertEquals("Krishna Sapkota", reader.getCellValueString(0, "a6"));
        assertEquals("Email", reader.getCellValueString(0, "d5"));
        assertEquals("ks_21285@hotmail.com", reader.getCellValueString(0, "d6"));
    }

    /* Tests for setters */

    @Test
    public void testSetCellValue() throws IOException {
        reader.setCellValue(0, "a1", 1);
        reader.setCellValue(0, "b1", 100);

        assertEquals(1.0, reader.getCellValueNumber(0, "a1"),1.0);
        assertEquals(100.0, reader.getCellValueNumber(0, "b1"),1.0);
    }


    /* Tests for conversions */

    @Test
    public void testConvertLettersToInteger() throws IOException {
        assertEquals(0,reader.convertLettersToInteger("A"));
        assertEquals(0,reader.convertLettersToInteger("a"));
        assertEquals(4,reader.convertLettersToInteger("E"));
        assertEquals(26,reader.convertLettersToInteger("aa"));
        assertEquals(27,reader.convertLettersToInteger("ab"));
    }

    @Test
    public void testSplitCellId() throws IOException {
        String[] cell1 = reader.splitCellId("e5");
        String[] cell2 = reader.splitCellId("Ae10");
        assertEquals("e", cell1[0]);
        assertEquals("5", cell1[1]);
        assertEquals("Ae", cell2[0]);
        assertEquals("10", cell2[1]);
    }


    private boolean contains(List<String> list1, List<String> list2) {
        boolean found = true;
        for (String item : list2) {
            if (!list1.contains(item)) {
                found = false;
                System.out.println(item);
                break;
            }
        }
        if (!found) {
            System.out.println(list1 +"\n" + list2);
        }
        return found;
    }
}
