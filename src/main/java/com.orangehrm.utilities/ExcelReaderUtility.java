package com.orangehrm.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtility {

    // get sheet data first
    // cell value

    public static List<String[]> getSheetData(String filePath, String sheetName) {
        // object of arraylist
        // data variable is defined as a list of array string
        List<String[]> data = new ArrayList<>();

        // object of work book
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook= new XSSFWorkbook()){
            Sheet sheet = workbook.getSheet(sheetName);
            if(sheet == null){
               throw new IllegalArgumentException("Sheet "+sheetName+ "does not exist");
            }

            // iterate through rows
            for(Row row:sheet) {
                if(row.getRowNum()==0) {
                    continue;
                }

                // Read all cells in the row
                List<String> rowData = new ArrayList<>();
                for(Cell cell:row) {
                    rowData.add(getCellValue(cell));
                }

                // convert rowData to String[]
                data.add(rowData.toArray(new String[0]));

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return data;

    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((int)cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                    default:
                        return "";
        }
    }

}
