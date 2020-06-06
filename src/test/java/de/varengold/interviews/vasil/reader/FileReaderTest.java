package de.varengold.interviews.vasil.reader;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.exceptions.NoCellFoundException;
import de.varengold.interviews.vasil.properties.ExcelProperties;
import de.varengold.interviews.vasil.service.ReverseNumberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FileReaderTest {

    private ExcelProperties excelProperties;

    private FileReader fileReader;

    @BeforeEach
    public void init() {
        this.excelProperties = new ExcelProperties();
        this.fileReader = new FileReader(excelProperties);
    }

    @Test
    public void testExtractValueFromSheet() {
        configureExcelProperties("/home/vasil/java11/src/main/resources/testFile.xlsx", "secondColumn", "testSheet");
        assertEquals(fileReader.extractValueFromSheet(), 5678);
    }

    @Test
    public void testExtractValueWithNullProperties() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.extractValueFromSheet();
        });
    }

    @Test
    public void testExtractValueWithInvalidColumnName() {
        assertThrows(NoCellFoundException.class, () -> {
            configureExcelProperties("/home/vasil/java11/src/main/resources/testFile.xlsx",
                                     "InvalidColumnName", "testSheet");
            fileReader.extractValueFromSheet();
        });
    }

    @Test
    public void testExtractValueWithInvalidSheet() {
        configureExcelProperties("/home/vasil/java11/src/main/resources/testFile.xlsx", null, "test");
        assertThrows(InvalidSheetException.class, () -> {
            fileReader.extractValueFromSheet();
        });
    }

    @Test
    public void testReadFileWithInvalidPath() {
        assertThrows(RuntimeException.class, () -> {
            configureExcelProperties(null, "targetColumn", null);
            fileReader.extractValueFromSheet();
        });
    }

    @Test
    public void testExtractValueWithNoValueForTheColumn() {
        assertThrows(NoCellFoundException.class, () -> {
            configureExcelProperties("/home/vasil/java11/src/main/resources/testFile.xlsx", "noValueBelow", "testSheet");
            fileReader.extractValueFromSheet();
        });
    }

    private void configureExcelProperties(final String fileName,
                                          final String columnName,
                                          final String sheetName) {
        excelProperties.setSheetName(sheetName);
        excelProperties.setFileName(fileName);
        excelProperties.setColumnName(columnName);
    }
}
