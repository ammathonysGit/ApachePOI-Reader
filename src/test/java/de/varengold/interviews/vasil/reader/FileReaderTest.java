package de.varengold.interviews.vasil.reader;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.properties.ExcelProperties;
import de.varengold.interviews.vasil.service.ReverseNumberService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public void testCorrespondingNumberPositiveValue() {
        assertEquals(fileReader.extractValueCorrespondingToColumnName(sheetExample(12345678, "testSheet", "targetColumn")), 12345678);
    }

    @Test
    public void testCorrespondingNumberNegativeValue() {
        assertEquals(fileReader.extractValueCorrespondingToColumnName(sheetExample(-12345678, "testSheet", "targetColumn")), -12345678);
    }

    @Test
    public void testCorrespondingNumberWithInvalidSheet() {
        assertThrows(InvalidSheetException.class, () -> {
           fileReader.extractValueCorrespondingToColumnName(null);
        });
    }

    private Sheet sheetExample(long value, String sheetName, String columnName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(0);
        Row secondRow = sheet.createRow(1);
        secondRow.createCell(0);
        sheet.getRow(0).getCell(0).setCellValue(columnName);
        sheet.getRow(1).getCell(0).setCellValue(value);

        return sheet;
    }
}
