package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelCopySpecificColumns {
    static String sourceFileA = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\a.xlsx";
    static String sourceFileB = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\b.xlsx";
    static String targetFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    public static void copyPastes(String fr, String to) {
        String sourceFilePath = fr;      // Path to the source Excel file
        String destinationFilePath = to; // Path to the destination Excel file
        int[] columnsToCopy = {3,4,5}; // 0-based indices of columns to copy (e.g., 1 = column B, 3 = column D)

        try (FileInputStream sourceFile = new FileInputStream(sourceFilePath);
             Workbook sourceWorkbook = new XSSFWorkbook(sourceFile);
             FileInputStream destinationFile = new FileInputStream(destinationFilePath);
             Workbook destinationWorkbook = new XSSFWorkbook(destinationFile)) {

            // Get the first sheet in both workbooks
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            Sheet destinationSheet = destinationWorkbook.getSheetAt(0);

            // Iterate through rows in the source sheet
            for (int rowNum = sourceSheet.getFirstRowNum(); rowNum <= sourceSheet.getLastRowNum(); rowNum++) {
                Row sourceRow = sourceSheet.getRow(rowNum);
                if (sourceRow == null) continue;

                // Ensure the corresponding row exists in the destination sheet
                Row destinationRow = destinationSheet.getRow(rowNum);
                if (destinationRow == null) {
                    destinationRow = destinationSheet.createRow(rowNum);
                }

                // Copy specified columns
                for (int col : columnsToCopy) {
                    Cell sourceCell = sourceRow.getCell(col);
                    if (sourceCell == null) continue;

                    Cell destinationCell = destinationRow.getCell(col);
                    if (destinationCell == null) {
                        destinationCell = destinationRow.createCell(col);
                    }

                    copyCell(sourceCell, destinationCell);
                }
            }

            // Write changes back to the destination file
            try (FileOutputStream outputStream = new FileOutputStream(destinationFilePath)) {
                destinationWorkbook.write(outputStream);
            }

            System.out.println("Specified columns copied successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyCell(Cell sourceCell, Cell destinationCell) {
        // Copy cell style
        CellStyle newCellStyle = destinationCell.getSheet().getWorkbook().createCellStyle();
        newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
        destinationCell.setCellStyle(newCellStyle);

        // Copy cell type and content
        switch (sourceCell.getCellType()) {
            case STRING:
                destinationCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                destinationCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case FORMULA:
                destinationCell.setCellFormula(sourceCell.getCellFormula());
                break;
            case BOOLEAN:
                destinationCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case ERROR:
                destinationCell.setCellErrorValue(sourceCell.getErrorCellValue());
                break;
            case BLANK:
                destinationCell.setBlank();
                break;
        }
    }
}

