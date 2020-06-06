package de.varengold.interviews.vasil.reader;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.exceptions.NoCellFoundException;
import de.varengold.interviews.vasil.properties.ExcelProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class SheetExtractorTest {

    private ExcelProperties excelProperties;

    private SheetExtractor sheetExtractor;

    @BeforeEach
    public void init() {
        this.excelProperties = new ExcelProperties();
        this.sheetExtractor = new SheetExtractor(excelProperties);
    }

    @Test
    public void testExtractValueFromSheet() throws IOException {
        configureExcelProperties("testFileExcel.xlsx", "secondColumn", "testSheet");
        assertEquals(sheetExtractor.extractValue(), 5678);
    }

    @Test
    public void testExtractValueWithNullProperties() {
        assertThrows(RuntimeException.class, () -> {
            sheetExtractor.extractValue();
        });
    }

    @Test
    public void testExtractValueWithInvalidColumnName() {
        assertThrows(NoCellFoundException.class, () -> {
            configureExcelProperties("testFileExcel.xlsx", "InvalidColumnName", "testSheet");
            sheetExtractor.extractValue();
        });
    }

    @Test
    public void testExtractValueWithInvalidSheet() throws IOException {
        configureExcelProperties("testFileExcel.xlsx", null, "test");
        assertThrows(InvalidSheetException.class, () -> {
            sheetExtractor.extractValue();
        });
    }

    @Test
    public void testReadFileWithInvalidPath() {
        assertThrows(RuntimeException.class, () -> {
            configureExcelProperties(null, "targetColumn", null);
            sheetExtractor.extractValue();
        });
    }

    @Test
    public void testExtractValueWithNoValueForTheColumn() {
        assertThrows(NoCellFoundException.class, () -> {
            configureExcelProperties("testFileExcel.xlsx", "noValueBelow", "testSheet");
            sheetExtractor.extractValue();
        });
    }

    private void configureExcelProperties(final String fileName,
                                          final String columnName,
                                          final String sheetName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        excelProperties.setSheetName(sheetName);
        excelProperties.setFileName(resource.getFile().getAbsolutePath());
        excelProperties.setColumnName(columnName);
    }
}
