package uk.ac.bcu.kbe.bioen;

/**
 * Created with IntelliJ IDEA.
 * User: ID118320
 * Date: 25/10/13
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest {

    protected final String excelFilename = this.getClass().getClassLoader().getResource("biogas-test.xlsx").getFile();
    protected final String excelCellsFilename = this.getClass().getClassLoader().getResource("excel-cells-test.csv").getFile();
    protected final String[][] variableCellMappings = {
                {"cows","0","d4","number","input"},
                {"area","0","d5","number","input"},
                {"energy","0","d50","formula", "output"},
                {"biogas","0","d74","formula", "output"},
        };




}
