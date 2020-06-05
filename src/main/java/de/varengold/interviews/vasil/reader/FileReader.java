package de.varengold.interviews.vasil.reader;

import java.io.File;
import java.io.IOException;

import de.varengold.interviews.vasil.exceptions.InvalidSheetException;
import de.varengold.interviews.vasil.properties.ExcelProperties;
import de.varengold.interviews.vasil.service.ReverseNumberService;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FileReader {

  private ExcelProperties excelProperties;
  private ReverseNumberService reverseNumberService;
  private static final String PATH_TO_FILES = "src/main/java/de/varengold/interviews/vasil/files/";

  @Autowired
  public FileReader(ExcelProperties excelProperties, ReverseNumberService reverseNumberService) {
    this.excelProperties = excelProperties;
    this.reverseNumberService = reverseNumberService;
  }


  public void readFile() {
    Workbook workbook;
    try {
      workbook = WorkbookFactory.create(new File(PATH_TO_FILES + excelProperties.getFileName()));
      Sheet sheet = workbook.getSheet(excelProperties.getSheetName());

      System.out.println(reverseNumberService.reverseNumber(extractValueCorrespondingToColumnName(sheet)));
    } catch (IOException e) {
      throw new RuntimeException("Couldn't find file at: " + PATH_TO_FILES + excelProperties.getFileName());
    }
  }

  public long extractValueCorrespondingToColumnName(Sheet sheet) {
    if (sheet == null)
      throw new InvalidSheetException("Sheet is invalid, cannot proceed with the extraction");

    int cellIndex = 0; //Here I will hold the Cell index that matches the name;
    long number = 0; //Creating a long because the number can be very large.

    for (Row currentRow : sheet) {
      for (Cell currentCell : currentRow) {
        if (currentCell.getCellType().equals(CellType.STRING) && //We are given a name so we are looking for a cell type of String
            StringUtils.isNotBlank(currentCell.getStringCellValue()) &&
            currentCell.getStringCellValue().equals(excelProperties.getColumnName())) {
          cellIndex = currentCell.getColumnIndex(); //This is the cell index of the given Column name.
        }
        if (currentCell.getCellType().equals(CellType.NUMERIC) &&
            !currentCell.getCellType().equals(CellType.BLANK) &&
            currentCell.getColumnIndex() == cellIndex) {
          number = (long) currentCell.getNumericCellValue();
          break;
        }
      }
    }
    return number;
  }

}
