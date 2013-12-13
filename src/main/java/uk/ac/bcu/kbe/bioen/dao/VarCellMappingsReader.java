package uk.ac.bcu.kbe.bioen.dao;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Its reads a csv file for variable and cell address mapping.
 * User: Krishna Sapkota
 * Date: 11/11/13
 * Time: 13:50
 */
public class VarCellMappingsReader {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final String filename;
    private List<VarCellMapping> varCellMappingList;

    public VarCellMappingsReader(String filename) throws IOException {
        this.filename = filename;
        varCellMappingList = process(filename);
    }

    List<VarCellMapping> process(String filename) throws IOException {
        List<VarCellMapping> varCellMappings = Lists.newArrayList();
        log.info("reading from file:"+filename);
        CSVReader reader = new CSVReader(new FileReader(filename));
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
             if (!nextLine[0].startsWith("//") && !nextLine[0].trim().isEmpty()) {
                varCellMappings.add( new VarCellMapping(nextLine[0].trim(),
                        Integer.parseInt(nextLine[1]),
                        nextLine[2].trim(),nextLine[3].trim(),
                        nextLine[4].trim()));
             }
        }
        log.info("total lines processes = "+varCellMappings.size());
        return varCellMappings;
    }

    public String[][] getArrayValues(){
        String[][] arrayValues = new String[varCellMappingList.size()][];
        int counter = 0;
        for (VarCellMapping varCellMapping : varCellMappingList) {
            String[] array={varCellMapping.getVar(),
                            String.valueOf(varCellMapping.getSheetIndex()),
                            varCellMapping.getCell(),
                            varCellMapping.getType(),
                            varCellMapping.getDirection()};
            arrayValues[counter]=array;
            counter ++;
        }
        return arrayValues;
    }

    private class VarCellMapping {
             private final String var;
             private final int sheetIndex;
             private final String cell;
             private final String type;
             private final String direction;
        private VarCellMapping(String var, int sheetIndex, String cell, String type, String direction) {
            this.var = var;
            this.sheetIndex = sheetIndex;
            this.cell = cell;
            this.type = type;
            this.direction = direction;
        }
        private String getVar() {return var;}
        private int getSheetIndex() { return sheetIndex;}
        private String getCell() {return cell;}
        private String getType() { return type;}
        private String getDirection() {return direction;}
    }
}
