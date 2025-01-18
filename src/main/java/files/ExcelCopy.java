package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelCopy {
    static String sourceFileA = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\a.xlsx";
    static String sourceFileB = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\b.xlsx";
    static String targetFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    public static void main(String[] args) {
        String sourceFilePath = sourceFileA; // Path to the source Excel file
        String destinationFilePath =targetFileC; // Path to the destination Excel file

        try (FileInputStream sourceFile = new FileInputStream(sourceFilePath);
             Workbook sourceWorkbook = new XSSFWorkbook(sourceFile);
             Workbook destinationWorkbook = new XSSFWorkbook()) {

            // Iterate through all sheets in the source workbook
            for (int i = 0; i < sourceWorkbook.getNumberOfSheets(); i++) {
                Sheet sourceSheet = sourceWorkbook.getSheetAt(i);
                Sheet destinationSheet = destinationWorkbook.createSheet(sourceSheet.getSheetName());

                // Copy rows and cells
                for (int rowNum = sourceSheet.getFirstRowNum(); rowNum <= sourceSheet.getLastRowNum(); rowNum++) {
                    Row sourceRow = sourceSheet.getRow(rowNum);
                    if (sourceRow == null) continue;

                    Row destinationRow = destinationSheet.createRow(rowNum);
                    for (int cellNum = sourceRow.getFirstCellNum(); cellNum < sourceRow.getLastCellNum(); cellNum++) {
                        Cell sourceCell = sourceRow.getCell(cellNum);
                        if (sourceCell == null) continue;

                        Cell destinationCell = destinationRow.createCell(cellNum);
                        copyCell(sourceCell, destinationCell);
                    }
                }
            }

            // Write to the destination file
            try (FileOutputStream outputStream = new FileOutputStream(destinationFilePath)) {
                destinationWorkbook.write(outputStream);
            }

            System.out.println("Excel file copied successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyCell(Cell sourceCell, Cell destinationCell) {
        // Copy cell style
        CellStyle newCellStyle = destinationCell.getSheet().getWorkbook().createCellStyle();
        newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
        destinationCell.setCellStyle(newCellStyle);

        // Copy cell comment
        if (sourceCell.getCellComment() != null) {
            destinationCell.setCellComment(sourceCell.getCellComment());
        }

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
