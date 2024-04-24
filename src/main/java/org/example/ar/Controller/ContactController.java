package org.example.ar.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class ContactController {
    private static final String EXCEL_FILE_PATH = "contact_details.xlsx";
    private static final String SHEET_NAME = "Contact Details";

    @PostMapping("/submitContact")
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            HttpServletResponse response, Model model) throws IOException {
        try {

            Workbook workbook;
            Sheet sheet;

            // Check if Excel file exists
            File file = new File(EXCEL_FILE_PATH);
            if (file.exists()) {
                // If file exists, open it and get the existing sheet
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet(SHEET_NAME);
            } else {
                // If file doesn't exist, create a new workbook and sheet
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(SHEET_NAME);
                // Create header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Name");
                headerRow.createCell(1).setCellValue("Email");
                headerRow.createCell(2).setCellValue("Subject");
                headerRow.createCell(3).setCellValue("Message");
            }

            // Get the last row number to append the new data
            int rowNum = sheet.getLastRowNum() + 1;

            // Create a new row and populate it with user's information
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(name);
            row.createCell(1).setCellValue(email);
            row.createCell(2).setCellValue(subject);
            row.createCell(3).setCellValue(message);

            // Write the workbook content to the file
            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fos);
            }


            model.addAttribute("errorMessage", "Your message has been received. We'll get back to you soon.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while processing your request");
        }
        return "contact";
    }

}
