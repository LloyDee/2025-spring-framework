package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelPasteColRow {
    static String sourceFileA = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\a.xlsx";
    static String sourceFileB = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\b.xlsx";
    static String targetFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    public static void main(String[] args) {
        String filePath =targetFileC; // Path to the Excel file
        int targetRow = 1; // Row index (0-based, e.g., 2 = 3rd row)
        int targetColumn = 11; // Column index (0-based, e.g., 3 = 4th column)
        String valueToPaste = System.getProperty("user.name"); // The value to paste into the specific cell

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // Get the first sheet (or specify the desired sheet by name)
            Sheet sheet = workbook.getSheetAt(0);

            // Get or create the target row
            Row row = sheet.getRow(targetRow);
            if (row == null) {
                row = sheet.createRow(targetRow);
            }

            // Get or create the target cell in the row
            Cell cell = row.getCell(targetColumn);
            if (cell == null) {
                cell = row.createCell(targetColumn);
            }

            // Set the value for the cell
            cell.setCellValue(valueToPaste);

            // Write the changes back to the file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }

            System.out.println("Value pasted successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

