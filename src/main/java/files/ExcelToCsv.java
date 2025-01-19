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

            // Iterate through each row
            for (Row row : sheet) {
                StringBuilder rowString = new StringBuilder();

                // Find the last cell index to handle empty cells correctly
                int lastCellNum = row.getLastCellNum();

                // Iterate through all cells up to the last cell index
                for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
                    Cell cell = row.getCell(cellIndex);

                    // Append cell value or empty if null
                    if (cell == null) {
                        rowString.append(""); // Empty cell
                    } else {
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
                                // Use cached formula result
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
                                    default:
                                        rowString.append("");
                                        break;
                                }
                                break;
                            case BLANK:
                                rowString.append("");
                                break;
                            default:
                                rowString.append("");
                        }
                    }
                    rowString.append(","); // Add comma as a delimiter
                }

                // Remove the last comma and add a new line
                if (!rowString.isEmpty()) {
                    rowString.setLength(rowString.length() - 1);
                }
                writer.println(rowString);
            }

            System.out.println("Conversion completed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error occurred while converting XLSX to CSV: " + e.getMessage());
        }
    }


}

