package com.careir.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

public final class ExcelReader {

    private static final Logger LOG = LogManager.getLogger(ExcelReader.class);

    private ExcelReader() {
    }

    public static Object[][] readSheet(String filesystemPath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filesystemPath)) {
            return readWorkbook(fis, sheetName);
        } catch (Exception e) {
            LOG.error("Failed to read Excel from path {}", filesystemPath, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads from {@code src/main/resources} or {@code src/test/resources} on the classpath (e.g. {@code testdata/login.xlsx}).
     */
    public static Object[][] readSheetFromClasspath(String classpathRelativePath, String sheetName) {
        try (InputStream in = Objects.requireNonNull(
                ExcelReader.class.getClassLoader().getResourceAsStream(classpathRelativePath),
                "Classpath resource not found: " + classpathRelativePath)) {
            return readWorkbook(in, sheetName);
        } catch (Exception e) {
            LOG.error("Failed to read Excel from classpath {}", classpathRelativePath, e);
            throw new RuntimeException(e);
        }
    }

    private static Object[][] readWorkbook(InputStream in, String sheetName) throws Exception {
        try (Workbook wb = WorkbookFactory.create(in)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }
            int rows = sheet.getPhysicalNumberOfRows();
            if (rows < 2) {
                return new Object[0][0];
            }
            int cols = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows - 1][cols];
            for (int i = 1; i < rows; i++) {
                Row r = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell c = r == null ? null : r.getCell(j);
                    data[i - 1][j] = cellAsString(c);
                }
            }
            return data;
        }
    }

    private static String cellAsString(Cell c) {
        if (c == null) {
            return "";
        }
        return switch (c.getCellType()) {
            case STRING -> c.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(c)
                    ? c.getLocalDateTimeCellValue().toString()
                    : NumberToTextConverter.toText(c.getNumericCellValue());
            case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
            case FORMULA -> cellAsString(c.getCachedFormulaResultType(), c);
            default -> "";
        };
    }

    private static String cellAsString(CellType type, Cell c) {
        return switch (type) {
            case STRING -> c.getStringCellValue();
            case NUMERIC -> NumberToTextConverter.toText(c.getNumericCellValue());
            case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
            case ERROR -> "";
            default -> "";
        };
    }
}
