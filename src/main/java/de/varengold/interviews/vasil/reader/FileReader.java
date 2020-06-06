package de.varengold.interviews.vasil.reader;

import java.io.IOException;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.exceptions.NoCellFoundException;
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
 * It has two dependencies which are ExcelProperties and ReverseNumberService.
 * ExcelProperties is used to get the  fileName, sheetName and columnName.
 * ReverseNumberService is used to reverse the value that has been extracted from the sheet.
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

  /**
   * Extracts value from a sheet by columnName by fileName, sheetName and reverses it.
   * @return the reversed number
   */
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
      log.error("Error: Sheet with name: " + excelProperties.getSheetName() + " couldn't be found.");
      throw new InvalidSheetException("Sheet doesn't exist cannot proceed with the extraction");
    }
    log.info("Extracting value from column: " + excelProperties.getColumnName());
    int cellIndex = 0; //Here I will hold the Cell index that matches the name;
    long number = 0; //Creating a long because the number can be very large.

    Row firstRow = sheet.getRow(0); // We can assume that here we have the column names.
    Cell desiredCell = null;

    for (Cell cellInFirstRow : firstRow) { // Go through the cells in the first row and search for the column by it's name
      if (CellType.STRING.equals(cellInFirstRow.getCellType()) &&
          cellInFirstRow.getStringCellValue().equals(excelProperties.getColumnName())) {
        desiredCell = cellInFirstRow;
        break;
      }
    }

    if (desiredCell == null) { // if we don't find a corresponding cell with the given columnName we can't proceed.
      log.error("Error: No Cell found with the name: " + excelProperties.getColumnName());
      throw new NoCellFoundException("No cell found with the name: " + excelProperties.getColumnName() + " cannot proceed");
    }

    cellIndex = desiredCell.getColumnIndex(); //if we find the cell with the corresponding name we get it's index

    for (Row currentRow : sheet) {
      if (currentRow == firstRow) {
        continue;
      }
      Cell cellCorrespondingIndex = currentRow.getCell(cellIndex); //Here we already know the index of the cell and we can directly access it.
      if (cellCorrespondingIndex != null &&
          CellType.NUMERIC.equals(cellCorrespondingIndex.getCellType()) &&
          cellCorrespondingIndex.getColumnIndex() == cellIndex) {
        number = (long) cellCorrespondingIndex.getNumericCellValue(); // find the first value and return it.
        return number;
      }

    }
    return number;
  }

}
