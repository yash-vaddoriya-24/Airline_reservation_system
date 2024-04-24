package org.example.ar.PDFGeneration;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.example.ar.Models.BookingDetail;
//import org.example.ar.Service.BookingDetailsService;
//import org.example.ar.Service.FlightDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Random;
//
//@Controller
//@RequestMapping("/booking")
//public class BookingController {
//
//    @Autowired
//    FlightDetailsService flightDetailsService;
//
//    @Autowired
//    BookingDetailsService bookingDetailsService;
//
//
//    @PostMapping("/confirm")
//    public String confirmBooking(@RequestParam("username") String username,
//                                 @RequestParam("email") String email,
//                                 @RequestParam("flight_id") String flightId,
//                                 @RequestParam("no_of_seats") int numberOfSeats,
//                                 @RequestParam("ticket_type") String ticketType,
//                                 @RequestParam("adults") int numberOfAdults,
//                                 @RequestParam("date_of_flight") String dateOfFlight,
//                                 HttpServletResponse response, HttpSession session) throws IOException {
//        int flightPrice = 0;
//        Long flightNoLong = Long.parseLong(flightId);
//        //verify the flight number
//        if (flightDetailsService.getAllFlightDetailsById(flightNoLong) == null) {
//            session.setAttribute("errorMessage", "No flight found for flight number " + flightNoLong);
//            return "redirect:/booking";
//        }
//        String PNRNumber = PNRNumberGeneration();
//        try {
//            BookingDetail bookingDetail = new BookingDetail(PNRNumber, email, flightId, username, dateOfFlight, numberOfSeats, ticketType, numberOfAdults);
//            bookingDetailsService.saveBookingDetail(bookingDetail);
//        } catch (Exception e) {
//            session.setAttribute("errorMessage", "An error occured while booking flight " + flightId);
//            return "redirect:/booking";
//        }
//
//        if (ticketType.equals("Business Class")){
//            flightPrice += 12000 * numberOfSeats;
//        }
//        else if (ticketType.equals("First Class")){
//            flightPrice += 15000 * numberOfSeats;
//        }
//        else if (ticketType.equals("Coach Ticket")){
//            flightPrice += 8000 * numberOfSeats;
//        }
//        // Generate PDF
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"booking_confirmation.pdf\"");
//
//        try (OutputStream out = response.getOutputStream()) {
//
//            Document document = new Document();
//            PdfWriter.getInstance(document, out);
//            document.open();
//            document.add(new Paragraph("Booking Confirmation"));
//            document.add(new Paragraph("PNR Number:" + PNRNumber));
//            document.add(new Paragraph("Passenger Name: " + username));
//            document.add(new Paragraph("Flight Number: " + flightId));
//            document.add(new Paragraph("Number of Seats: " + numberOfSeats));
//            document.add(new Paragraph("Ticket Type: " + ticketType));
//            document.add(new Paragraph("Number of Adults: " + numberOfAdults));
//            document.add(new Paragraph("Date of Flight: " + dateOfFlight));
//            document.add(new Paragraph("Flight Price: " + flightPrice));
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//            session.setAttribute("errorMessage", "An error occurred while booking flight");
//        }
//        return "redirect:/booking";
//    }
//
//    public String PNRNumberGeneration() {
//        String prefix = "PNR";
//        Long timestamp = System.currentTimeMillis();
//        Long randomNumber = new Random().nextLong(10000);
//        return prefix + timestamp + randomNumber;
//    }
//}
//
//
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ar.Models.BookingDetail;
import org.example.ar.Service.BookingDetailsService;
import org.example.ar.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    FlightDetailsService flightDetailsService;

    @Autowired
    BookingDetailsService bookingDetailsService;

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("flight_id") String flightId,
                                 @RequestParam("no_of_seats") int numberOfSeats,
                                 @RequestParam("ticket_type") String ticketType,
                                 @RequestParam("adults") int numberOfAdults,
                                 @RequestParam("date_of_flight") String dateOfFlight,
                                 HttpServletResponse response, HttpSession session) throws IOException {
        int flightPrice = 0;
        Long flightNoLong = Long.parseLong(flightId);

        if (flightDetailsService.getAllFlightDetailsById(flightNoLong) == null) {
            session.setAttribute("errorMessage", "No flight found for flight number " + flightNoLong);
            return "redirect:/booking";
        }
        String PNRNumber = PNRNumberGeneration();
        try {
            BookingDetail bookingDetail = new BookingDetail(PNRNumber, email, flightId, username, dateOfFlight, numberOfSeats, ticketType, numberOfAdults);
            bookingDetailsService.saveBookingDetail(bookingDetail);
        } catch (Exception e) {
            session.setAttribute("errorMessage", "An error occurred while booking flight " + flightId);
            return "redirect:/booking";
        }

        // Calculate flight price based on ticket type
        if (ticketType.equals("Business Class")) {
            flightPrice += 12000 * numberOfSeats;
        } else if (ticketType.equals("First Class")) {
            flightPrice += 15000 * numberOfSeats;
        } else if (ticketType.equals("Coach Ticket")) {
            flightPrice += 8000 * numberOfSeats;
        }

        // Generate PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"booking_confirmation.pdf\"");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Add Airline Logo
            Image logo = Image.getInstance("src/main/resources/static/Images/logo.jpg");
            logo.scaleAbsolute(150, 50);
            document.add(logo);

            // Add Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Paragraph title = new Paragraph("Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add PNR Number
            Paragraph pnrParagraph = new Paragraph("PNR Number: " + PNRNumber);
            document.add(pnrParagraph);

            // Create Table for Passenger and Flight Details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);

            // Add Passenger Details
            PdfPCell passengerCell = new PdfPCell(new Paragraph("Passenger Details"));
            passengerCell.setColspan(2);
            passengerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(passengerCell);

            table.addCell("Passenger Name:");
            table.addCell(username);
            table.addCell("Email:");
            table.addCell(email);

            // Add Flight Details
            PdfPCell flightCell = new PdfPCell(new Paragraph("Flight Details"));
            flightCell.setColspan(2);
            flightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(flightCell);

            table.addCell("Flight Number:");
            table.addCell(flightId);
            table.addCell("Date of Flight:");
            table.addCell(dateOfFlight);
            table.addCell("Number of Seats:");
            table.addCell(String.valueOf(numberOfSeats));
            table.addCell("Ticket Type:");
            table.addCell(ticketType);
            table.addCell("Number of Adults:");
            table.addCell(String.valueOf(numberOfAdults));
            table.addCell("Flight Price:");
            table.addCell(String.valueOf(flightPrice));

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred while booking flight");
        }
        return "redirect:/booking";
    }

    public String PNRNumberGeneration() {
        String prefix = "PNR";
        Long timestamp = System.currentTimeMillis();
        Long randomNumber = new Random().nextLong(10000);
        return prefix + timestamp + randomNumber;
    }
}

