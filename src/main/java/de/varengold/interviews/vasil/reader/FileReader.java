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
import org.springframework.core.io.FileSystemResource;
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

  @Autowired
  public FileReader(ExcelProperties excelProperties) {
    this.excelProperties = excelProperties;
  }

  /**
   * Extracts value from a sheet by columnName by fileName, sheetName.
   * @return the reversed number
   */
  public long extractValueFromSheet() {
    try {
      if (excelProperties == null) {
        log.error("Properties are null can't proceed further");
        throw new RuntimeException("Properties are invalid.");
      }
      log.info("Starting to read file: {}", excelProperties.getFileName());
      Sheet sheet = loadSheet();

      return (long) extractCell(sheet).getNumericCellValue();
    } catch (IOException e) {
      log.error("Error: Couldn't find file {}:", excelProperties.getFileName());
      throw new RuntimeException("Couldn't find file:" + excelProperties.getFileName());
    }
  }

  private Sheet loadSheet() throws IOException {
    FileSystemResource excelFile = new FileSystemResource(excelProperties.getFileName());
    Workbook workbook = WorkbookFactory.create(excelFile.getFile());
    return workbook.getSheet(excelProperties.getSheetName());
  }

  private Cell extractCell(Sheet sheet) {
    if (sheet == null) {
      log.error("Error: Sheet with name: {} couldn't be found.", excelProperties.getSheetName());
      throw new InvalidSheetException("Sheet doesn't exist cannot proceed with the extraction");
    }
    log.info("Extracting value from column: {}", excelProperties.getColumnName());
    //Here I will hold the Cell index that matches the name;
    int cellIndex = 0;
    Cell cellToReturn = null;

    // We can assume that here we have the column names.
    Row firstRow = sheet.getRow(0);
    Cell desiredCell = null;

    // Go through the cells in the first row and search for the column by it's name
    for (Cell cellInFirstRow : firstRow) {
      if (CellType.STRING.equals(cellInFirstRow.getCellType()) &&
          cellInFirstRow.getStringCellValue().equals(excelProperties.getColumnName())) {
        desiredCell = cellInFirstRow;
        break;
      }
    }
    // if we don't find a corresponding cell with the given columnName we can't proceed.
    if (desiredCell == null) {
      log.error("Error: No Cell found with the name: {}", excelProperties.getColumnName());
      throw new NoCellFoundException("No cell found with the name: " + excelProperties.getColumnName() + " cannot proceed");
    }
    //if we find the cell with the corresponding name we get it's index
    cellIndex = desiredCell.getColumnIndex();

    for (Row currentRow : sheet) {
      if (currentRow.equals(firstRow)) {
        continue;
      }
      //Here we already know the index of the cell and we can directly access it.
      Cell cellCorrespondingIndex = currentRow.getCell(cellIndex);
      if (cellCorrespondingIndex != null &&
          CellType.NUMERIC.equals(cellCorrespondingIndex.getCellType()) &&
          cellCorrespondingIndex.getColumnIndex() == cellIndex) {
          cellToReturn = cellCorrespondingIndex;
          break;
      }
    }
    //If all the Cells below the given columnName are empty we throw exception
    if (cellToReturn == null) {
        log.error("Column with name: {} doesn't have value in Cells underneath it", excelProperties.getColumnName());
        throw new NoCellFoundException("Couldn't find any Cell with value under the column: " + excelProperties.getColumnName());
    }
    return cellToReturn;
  }

}
