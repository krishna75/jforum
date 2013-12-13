package uk.ac.bcu.kbe.bioen.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.bcu.kbe.bioen.Settings.LoggingMode;
import uk.ac.bcu.kbe.bioen.Settings.Settings;
import uk.ac.bcu.kbe.bioen.dao.ExcelReader;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ID118320
 * Date: 22/10/13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class BiogasManager {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ExcelReader excelReader;
    private final String[][] varCellMappings;

    public BiogasManager(ExcelReader excelReader, String[][] varCellMappings) throws IOException {
        this. excelReader = excelReader;
        this.varCellMappings = varCellMappings;
    }

    public void setInput(String jsonString) throws ParseException {
        if (Settings.mode != LoggingMode.NONE){log.info("parsing string to json : "+jsonString);}

        Gson gson = new Gson();
        Map<String,String> map =  gson.fromJson(jsonString , Map.class);
        for (String key : map.keySet()) {
            String sheetIndex = getMapping(key)[1];
            String cellId = getMapping(key)[2];
            String value =  map.get(key);
            excelReader.setCellValue(Integer.parseInt(sheetIndex), cellId, Integer.parseInt(value));
        }
    }

    public void update() throws IOException {
        Multimap<Integer, String> sheetCells = ArrayListMultimap.create();
        for (String[] varCellArray : varCellMappings) {
                if (varCellArray[3].equals("formula")){
                    Integer sheetIndex = Integer.parseInt(varCellArray[1]);
                    sheetCells.put(sheetIndex, varCellArray[2]);
                }
        }
        excelReader.update(sheetCells);
    }

    String[] getMapping(String variable) {
        for (String[] varCellMapping : varCellMappings) {
            if (varCellMapping[0].equals(variable)) {
                return varCellMapping;
            }
        }
        return null;
    }

    public String getOutput() {
        JSONObject map = new JSONObject();
        for (String[] varCellMapping : varCellMappings) {
            String sheetIndex = varCellMapping[1];
            String cellId = varCellMapping[2];

            double value = excelReader.getCellValueNumber(Integer.parseInt(sheetIndex), cellId);
            String variable = varCellMapping[0];

            map.put(variable, value);
        }
        return map.toJSONString();
    }
}
