package de.varengold.interviews.vasil.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExcelPropertiesTest {

    @Autowired
    private ExcelProperties excelProperties;

    @Autowired
    Environment environment;

    private final String FILE_NAME = "app.fileName";

    private final String COLUMN_NAME = "app.columnName";

    private final String SHEET_NAME = "app.sheetName";

    @Test
    void testExcelProperties() {
        assertEquals(environment.getProperty(FILE_NAME), excelProperties.getFileName());
        assertEquals(environment.getProperty(COLUMN_NAME), excelProperties.getColumnName());
        assertEquals(environment.getProperty(SHEET_NAME), excelProperties.getSheetName());
    }
}
