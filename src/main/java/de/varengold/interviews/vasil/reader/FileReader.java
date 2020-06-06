package de.varengold.interviews.vasil.reader;

import java.io.IOException;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.properties.ExcelProperties;
import de.varengold.interviews.vasil.service.ReverseNumberService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * FileReader class is responsible for reading a particular value from a given fileName, sheetName, columnName that are configured
 * in the application.properties file and then injected into the ExcelProperties class.
 * @author VVasilev
 */
@Service
@Getter
@Slf4j
public class FileReader {

  private final ExcelProperties excelProperties;
  private final ReverseNumberService reverseNumberService;

  @Autowired
  public FileReader(ExcelProperties excelProperties, ReverseNumberService reverseNumberService) {
    this.excelProperties = excelProperties;
    this.reverseNumberService = reverseNumberService;
  }


  public long extractValueFromSheet() {
    try {
      if (excelProperties == null) {
        log.error("Properties are null can't proceed further");
        throw new RuntimeException("Properties are invalid.");
      }
      log.info("Starting to read file: " + excelProperties.getFileName());
      Resource resource = new ClassPathResource(excelProperties.getFileName());
      Workbook workbook = WorkbookFactory.create(resource.getFile());
      Sheet sheet = workbook.getSheet(excelProperties.getSheetName());

     return reverseNumberService.reverseNumber(extractValue(sheet));
    } catch (IOException e) {
      log.error("Error: Couldn't find file:" + excelProperties.getFileName());
      throw new RuntimeException("Couldn't find file:" + excelProperties.getFileName());
    }
  }

  private long extractValue(Sheet sheet) {
    if (sheet == null) {
      log.error("Error: Sheet is invalid.");
      throw new InvalidSheetException("Sheet is invalid, cannot proceed with the extraction");
    }
    log.info("Extracting value from column.");
    int cellIndex = 0; //Here I will hold the Cell index that matches the name;
    long number = 0; //Creating a long because the number can be very large.

    for (Row currentRow : sheet) {
      for (Cell currentCell : currentRow) {
        if (CellType.STRING.equals(currentCell.getCellType()) && //We are given a name so we are looking for a cell type of String
            currentCell.getStringCellValue().equals(excelProperties.getColumnName())) {
          cellIndex = currentCell.getColumnIndex(); //This is the cell index of the given Column name.
        }
        if (CellType.NUMERIC.equals(currentCell.getCellType()) &&
            currentCell.getColumnIndex() == cellIndex) {
          number = (long) currentCell.getNumericCellValue();
          break;
        }
      }
    }
    return number;
  }

}
