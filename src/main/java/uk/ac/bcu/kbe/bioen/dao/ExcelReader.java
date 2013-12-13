package uk.ac.bcu.kbe.bioen.dao;


import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.bcu.kbe.bioen.Settings.LoggingMode;
import uk.ac.bcu.kbe.bioen.Settings.Settings;

import java.io.*;
import java.util.Iterator;
import java.util.List;


/**
* Created with IntelliJ IDEA.
* User: ID118320
* Date: 11/10/13
* Time: 12:10
*/

public class ExcelReader {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private  XSSFWorkbook workbook;
    private final String filename;
    private FileInputStream fileInputStream;

    public ExcelReader(String filename) throws IOException {
        this.filename = filename;
        workbook = process(filename);
    }

    XSSFWorkbook process(String filename) throws IOException {
        log.info("opening file : "+ filename);
        fileInputStream =   new FileInputStream(new File(filename));
        return new XSSFWorkbook(fileInputStream);
    }

    public void update(Multimap<Integer,String> sheetCells) throws IOException {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        for (Integer sheetIndex : sheetCells.keySet()) {
            for (String cellId : sheetCells.get(sheetIndex)) {
                evaluator.evaluateFormulaCell(getCell(sheetIndex, cellId));
            }
        }
    }

    public List<String> getRowValues(int sheetIndex, int rowIndex) {
        List<String>  rowValues = Lists.newArrayList();

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowIndex);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            rowValues.add(getValue(cell));
        }
        return rowValues;
    }

    public String getCellValue(int sheetIndex, int rowIndex, int columnIndex) {
        return  getValue(getCell(sheetIndex,rowIndex,columnIndex));
    }

    public double getCellValueNumber(int sheetIndex, String cellId) {
        double doubleValue = Double.parseDouble(getValue(getCell(sheetIndex, cellId)));
        return  doubleValue;
    }

    public String getCellValueString(int sheetIndex, String cellId) {
        return getValue(getCell(sheetIndex, cellId));
    }

    public void setCellValue(int sheetIndex, int rowIndex, int colIndex, int value) {
        Cell cell = getCell(sheetIndex, rowIndex,colIndex);
        cell.setCellValue(value);
    }

    public void setCellValue(int sheetIndex, String cellId, int value) {
        if (Settings.mode != LoggingMode.NONE){log.info("setting cell value: "+value+" for cell "+cellId); }
        Cell cell = getCell(sheetIndex, cellId);
        cell.setCellValue(value);
    }

    /* Non public stuff */


    String[] splitCellId(String cellId) {
        return  cellId.split("(?<=\\p{L})(?=\\d)") ;
    }

     int convertLettersToInteger(String letters) {
        int index = 0;
        char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] chars = letters.toLowerCase().toCharArray();

        int count = 0;
        for (char aChar : chars) {
            for (int i = 0; i < 26; i++) {
                if (aChar == alphabets[i]) {
                    index = index + count + i;
                }
            }
            count += 26;
        }
        return index;
    }

    private Cell getCell(int sheetIndex, String cellId) {
        String[] splittedId = splitCellId(cellId);
        int row = Integer.parseInt(splittedId[1]);
        int col = convertLettersToInteger(splittedId[0]);
        return getCell(sheetIndex, row, col);
    }

    private Cell getCell(int sheetIndex, int rowIndex, int columnIndex) {
        rowIndex -= 1;
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.getRow(rowIndex).getCell(columnIndex);
    }

    private String getValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
            }
        }
        return cellValue;
    }

}
