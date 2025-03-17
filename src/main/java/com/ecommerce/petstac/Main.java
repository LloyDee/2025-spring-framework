package com.ecommerce.petstac;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Path files = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "ecommerce" + File.separator + "petstac" + File.separator + "files");
    private static final Path reports = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "ecommerce" + File.separator + "petstac" + File.separator + "reports");

    public static void main(String[] args) {
        modFiles();
    }

    public static void modFiles() {
        try (FileInputStream file = new FileInputStream(new File(reports + File.separator + "hello.xlsx"));
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheet("Test123");
            Row row = sheet.getRow(1);
            Cell cell = row.getCell(1);
            switch (cell.getCellType()) {
                case STRING:
                    System.out.print(cell.getStringCellValue() + "\t");
                    break;
                case NUMERIC:
                    System.out.print(cell.getNumericCellValue() + "\t");
                    break;
                case BOOLEAN:
                    System.out.print(cell.getBooleanCellValue() + "\t");
                    break;
                default:
                    System.out.print("UNKNOWN\t");
            }

        } catch (Exception e) {
            System.err.println("Error opening Excel file: " + e.getMessage());
        }
    }

    public static void createDirectory() {
        try {
            Files.createDirectories(files);
            System.out.println("Directory created successfully: " + reports);
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void deleteFile(Path filePath) {
        try {
            Files.delete(filePath);
            System.out.println("File deleted successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }

    public static void createFile(Path filePath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); // Create an Excel workbook
             FileOutputStream fileOut = new FileOutputStream(String.valueOf(filePath))) {

            XSSFSheet sheet = workbook.createSheet("Test123"); // Create a sheet
//
//            // Create a row and a cell
            Row firstRow = sheet.createRow(0);
            Cell firstColumn = firstRow.createCell(0);
            firstColumn.setCellValue("Hello");
            firstColumn = firstRow.createCell(1);
            firstColumn.setCellValue("World!");

            workbook.write(fileOut); // Write to file
            System.out.println("Excel file created successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
        }
    }
}
