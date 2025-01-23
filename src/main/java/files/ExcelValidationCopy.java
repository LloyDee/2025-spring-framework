package files;

import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelValidationCopy {
    public static void main(String[] args) {
        String sourceFilePath = "C:\\Users\\loyd_\\Desktop\\source.xlsx"; // Path to the source Excel file
        String targetFilePath = "C:\\Users\\loyd_\\Desktop\\target.xlsx"; // Path to the target Excel file

        try (FileInputStream sourceFile = new FileInputStream(sourceFilePath);
             FileInputStream targetFile = new FileInputStream(targetFilePath);
             XSSFWorkbook sourceWorkbook = new XSSFWorkbook(sourceFile);
             XSSFWorkbook targetWorkbook = new XSSFWorkbook(targetFile)) {

            XSSFSheet sourceSheet = sourceWorkbook.getSheetAt(0); // Source sheet (adjust index if needed)
            XSSFSheet targetSheet = targetWorkbook.createSheet("Target"); // Create a target sheet

            // Copy validations from source to target
            for (XSSFDataValidation validation : sourceSheet.getDataValidations()) {
                CellRangeAddressList regions = validation.getRegions();
                DataValidationConstraint constraint = validation.getValidationConstraint();

                XSSFDataValidationHelper helper = new XSSFDataValidationHelper(targetSheet);
                XSSFDataValidation newValidation = (XSSFDataValidation) helper.createValidation(constraint, regions);
                newValidation.setErrorStyle(validation.getErrorStyle());
                newValidation.setEmptyCellAllowed(validation.getEmptyCellAllowed());
                newValidation.setSuppressDropDownArrow(validation.getSuppressDropDownArrow());
                newValidation.setShowErrorBox(validation.getShowErrorBox());
                newValidation.setShowPromptBox(validation.getShowPromptBox());
                targetSheet.addValidationData(newValidation);
            }

            // Save the target workbook
            try (FileOutputStream outFile = new FileOutputStream(targetFilePath)) {
                targetWorkbook.write(outFile);
            }

            System.out.println("Data validations copied successfully!");

        } catch (IOException e) {
            System.err.println("Error while copying data validations: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
