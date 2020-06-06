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

    private ReverseNumberService reverseNumberService;

    private FileReader fileReader;

    @BeforeEach
    public void init() {
        this.excelProperties = new ExcelProperties();
        this.reverseNumberService = new ReverseNumberService();
        this.fileReader = new FileReader(excelProperties, reverseNumberService);
    }

    @Test
    public void testExtractValueFromSheet() {
        configureExcelProperties("testFile.xlsx", "secondColumn", "testSheet");
        assertEquals(fileReader.extractValueFromSheet(), 8765);
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
            configureExcelProperties("testFile.xlsx", "InvalidColumnName", "testSheet");
            fileReader.extractValueFromSheet();
        });
    }

    @Test
    public void testExtractValueWithInvalidSheet() {
        configureExcelProperties("testFile.xlsx", null, "test");
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

    private void configureExcelProperties(final String fileName,
                                          final String columnName,
                                          final String sheetName) {
        excelProperties.setSheetName(sheetName);
        excelProperties.setFileName(fileName);
        excelProperties.setColumnName(columnName);
    }
}
