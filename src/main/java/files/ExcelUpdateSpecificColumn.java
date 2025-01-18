package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUpdateSpecificColumn {
    static String sourceFileA = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\a.xlsx";
    static String sourceFileB = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\b.xlsx";
    static String targetFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    public static void main(String[] args) {
        String filePath = targetFileC; // Path to the existing Excel file
        int targetColumn = 2; // Column to update (0-based index)
        String[] newData = {System.getProperty("user.name"),"Dsds"}; // Data to paste

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // Get the first sheet (or specify the desired sheet)
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows and update the target column
            for (int i = 0; i < newData.length; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                Cell cell = row.getCell(targetColumn);
                if (cell == null) {
                    cell = row.createCell(targetColumn);
                }

                // Update the cell value
                cell.setCellValue(newData[i]);
            }

            // Write changes back to the file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }

            System.out.println("Column updated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
