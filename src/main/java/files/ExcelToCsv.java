package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class ExcelToCsv {
    static String sourceFileC = System.getProperty("user.dir") + "\\src\\main\\java\\reports\\c.xlsx";
    static String targetFileD = System.getProperty("user.dir") + "\\src\\main\\java\\reports\\d.csv";

    public static void main(String[] args) {

        convertXlsxToCsv(sourceFileC, targetFileD);
        System.out.println("Conversion completed successfully!");
    }
    public static void convertXlsxToCsv(String inputFilePath, String outputFilePath) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(inputFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                StringBuilder rowString = new StringBuilder();

                // Iterate through the cells of the row
                for (Cell cell : row) {
                    // Get the cell value as a string, regardless of type
                    switch (cell.getCellType()) {
                        case STRING:
                            rowString.append(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowString.append(cell.getDateCellValue());
                            } else {
                                rowString.append(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            rowString.append(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            // Get the cached formula result value
                            switch (cell.getCachedFormulaResultType()) {
                                case STRING:
                                    rowString.append(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        rowString.append(cell.getDateCellValue());
                                    } else {
                                        rowString.append(cell.getNumericCellValue());
                                    }
                                    break;
                                case BOOLEAN:
                                    rowString.append(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                    rowString.append("----");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case BLANK:
                            break;
                        default:
                    }
                    rowString.append(","); // Add a comma to separate values
                }

                // Remove the last comma and add a new line
                if (!rowString.isEmpty()) {
                    rowString.setLength(rowString.length() - 1);
                }
                writer.println(rowString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error occurred while converting XLSX to CSV: " + e.getMessage());
        }
    }


}

