package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelToCsv {
    static String sourceFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    static String targetFileD = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\d.csv";
    public static void main(String[] args) {
        String excelFilePath =sourceFileC; // Path to the Excel file
        String csvFilePath = targetFileD;     // Path to the CSV file

        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream);
             FileWriter csvWriter = new FileWriter(csvFilePath)) {

            // Get the first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through each row in the sheet
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Iterate through each cell in the row
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);

                    // Write the cell data to CSV, adding quotes if necessary
                    if (cell != null) {
                        String cellValue = getCellValueAsString(cell);
                        // Add a comma after each value except the last one in the row
                        if (j > 0) {
                            csvWriter.append(",");
                        }
                        csvWriter.append(cellValue);
                    }
                }

                // Write a new line after each row
                csvWriter.append("\n");
            }

            System.out.println("Excel file has been successfully converted to CSV!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get cell value as String (handles different types of cells)
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
