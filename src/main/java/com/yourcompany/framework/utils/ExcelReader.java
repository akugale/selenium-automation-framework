package com.yourcompany.framework.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {
    public static Object[][] readSheet(String path, String sheetName) {
        try (FileInputStream fis = new FileInputStream(path)) {
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows-1][cols];
            for (int i = 1; i < rows; i++) {
                Row r = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell c = r.getCell(j);
                    data[i-1][j] = c == null ? "" : switch (c.getCellType()) {
                        case STRING -> c.getStringCellValue();
                        case NUMERIC -> String.valueOf(c.getNumericCellValue());
                        case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
                        default -> "";
                    };
                }
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
