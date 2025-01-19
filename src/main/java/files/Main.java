package files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Main {
   static ExcelCopySpecificColumns excel ;
    static String sourceFileA = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\a.xlsx";
    static String sourceFileB = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\b.xlsx";
    static String targetFileC = System.getProperty("user.dir")+"\\src\\main\\java\\reports\\c.xlsx";
    public static void main(String[] args) throws IOException {
        copyWithFormula(sourceFileA,targetFileC);
        ExcelCopySpecificColumns.copyPastes(sourceFileB,targetFileC);
        ExcelToCsv.convertXlsxToCsv(targetFileC,ExcelToCsv.targetFileD);
    }


    public static void copyWithFormula(String fromFile, String toFile) throws IOException {
        try {
            // Load the source workbook
            FileInputStream sourceFile = new FileInputStream(fromFile);
            Workbook sourceWorkbook = new XSSFWorkbook(sourceFile);

            // Create the destination workbook
            Workbook destinationWorkbook = new XSSFWorkbook();

            // Copy the sheet from source to destination
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            Sheet destinationSheet = destinationWorkbook.createSheet(sourceSheet.getSheetName());

            // Copy cells and formulas
            copySheet(sourceSheet, destinationSheet);

            // Save the destination workbook
            FileOutputStream outputStream = new FileOutputStream(toFile);
            destinationWorkbook.write(outputStream);
            outputStream.close();

            // Close the workbooks
            sourceWorkbook.close();
            destinationWorkbook.close();
            sourceFile.close();

            System.out.println("Excel file copied successfully with formulas.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copySheet(Sheet sourceSheet, Sheet destinationSheet) {
        for (Row sourceRow : sourceSheet) {
            Row destinationRow = destinationSheet.createRow(sourceRow.getRowNum());
            for (Cell sourceCell : sourceRow) {
                Cell destinationCell = destinationRow.createCell(sourceCell.getColumnIndex());
                copyCell(sourceCell, destinationCell);
            }
        }
    }
    private static void copyCell(Cell sourceCell, Cell destinationCell) {
        switch (sourceCell.getCellType()) {
            case STRING:
                destinationCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                destinationCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BOOLEAN:
                destinationCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                destinationCell.setCellFormula(sourceCell.getCellFormula());
                break;
            default:
                break;
        }
    }
    public static void pasteToExcel(String fromFile, String toFile) throws IOException {

        int[] sourceColumns = {6}; // Indices of columns to copy (starting from 0)

        try (FileInputStream fis = new FileInputStream(fromFile);
             Workbook sourceWorkbook = new XSSFWorkbook(fis);
             FileOutputStream fos = new FileOutputStream(toFile);
             Workbook destinationWorkbook = new XSSFWorkbook()) {

            Sheet sourceSheet = sourceWorkbook.getSheetAt(0); // Assuming you want the first sheet
            Sheet destinationSheet = destinationWorkbook.createSheet();

            for (Row sourceRow : sourceSheet) {
                Row destinationRow = destinationSheet.createRow(sourceRow.getRowNum());
                for (int colIndex : sourceColumns) {
                    Cell sourceCell = sourceRow.getCell(colIndex);
                    if (sourceCell != null) {
                        Cell destinationCell = destinationRow.createCell(colIndex);
                        switch (sourceCell.getCellType()){
                            case STRING:
                                destinationCell.setCellValue(sourceCell.getStringCellValue());
                                break;
                            case NUMERIC:
                                destinationCell.setCellValue(sourceCell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                destinationCell.setCellValue(sourceCell.getBooleanCellValue());
                                break;
                            case ERROR:
                                destinationCell.setCellErrorValue(sourceCell.getErrorCellValue());
                                break;
                            case FORMULA:
                                destinationCell.setCellFormula(sourceCell.getCellFormula());
                                break;
                            default:
                                break;
                        }

                    }
                }
            }

            destinationWorkbook.write(fos);
            System.out.println("Data file copied successfully!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
