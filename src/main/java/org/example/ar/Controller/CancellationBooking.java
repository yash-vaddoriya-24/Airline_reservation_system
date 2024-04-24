package org.example.ar.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.ar.EmailService.JavaSmtpGmailSenderApplication;
import org.example.ar.Models.BookingDetail;
import org.example.ar.Repository.BookingDetailRepository;
import org.example.ar.Service.BookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class CancellationBooking {

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Autowired
    BookingDetailsService bookingDetailsService;

    @Autowired
    JavaSmtpGmailSenderApplication javaSmtpGmailSenderApplication;

    @PostMapping("Cancellation")
    public String PNRCancelBooking(@RequestParam String PNR, Model model, HttpSession session) {
        try {
            BookingDetail bookingDetail = bookingDetailRepository.findBookingDetailByPNR(PNR);
            if (bookingDetail != null) {
                try {
                    //otp generation
                    int otp = generateOTP();
                    long otpCreationTime = System.currentTimeMillis();
                    String subject = "Booking cancellation Request";
                    String body = "Dear, " + bookingDetail.getUsername() + "\n" +
                            "\n" +
                            "You are receiving this email because a request has been made to cancel the flight booking for your account. If you did not request for cancellation, please disregard this email.To proceed with the password reset, please use the following One-Time Password (OTP):\n" +
                            "\n" +
                            otp + "\n" + "Please note that this OTP is valid for a limited time only and should be used immediately.\n" +
                            "\n" +
                            "If you initiated this request, please enter the OTP provided above to cancel your booking. \n" +
                            "\n" +
                            "If you did not initiate this request, we recommend reviewing the security of your account and taking appropriate measures to ensure its safety.\n" +
                            "\n" +
                            "Thank you for choosing AIRRESERVE.\n" +
                            "\n" +
                            "Sincerely,\n" +
                            "AIRRESERVE Team";

                    //sending otp
                    javaSmtpGmailSenderApplication.sendMail(bookingDetail.getEmail(), subject, body);
                    model.addAttribute("PNRNumber", PNR);
                    model.addAttribute("errorMessage", "OTP has been sent to the registered email id");
                    model.addAttribute("otpGenerated", "otp sended successfully");
                    session.setAttribute("otp", otp);
                    session.setAttribute("otpCreationTime", otpCreationTime);
                    return "Cancellation";
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "An error occurred while cancelling the booking");
                }
            } else {
                model.addAttribute("errorMessage", "PNR not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while cancelling the booking");
        }
        return "Cancellation";
    }


    @PostMapping("OTPVerification")
    public String OTPVerification(@RequestParam("otp") int userOtp, @RequestParam("PNRNumber") String PNR, Model model, HttpSession session) {
        try {
            if (session.getAttribute("otp") != null) {
                int maxInactiveIntervalInSeconds = 150; // 150 seconds
                session.setMaxInactiveInterval(maxInactiveIntervalInSeconds);
                Integer storedOTP = (Integer) session.getAttribute("otp");
                Long otpOperationTime = (Long) session.getAttribute("otpCreationTime");

                if (storedOTP != null && otpOperationTime != null) {
                    long currentTime = System.currentTimeMillis();
                    long otpAge = currentTime - otpOperationTime;
                    long otpExpirationTime = 5 * 60 * 1000;

                    if (otpAge <= otpExpirationTime) {
                        if (userOtp == (storedOTP)) {
                            session.removeAttribute("otp");
                            session.removeAttribute("otpCreationTime");
                            Integer rowAffected = bookingDetailsService.deleteBookingDetailByPNR(PNR);

                            if (rowAffected != null && rowAffected > 0) {
                                model.addAttribute("errorMessage", "Booking detail deleted successfully");
                                session.invalidate();
                            } else {
                                model.addAttribute("errorMessage", "An error occurred while deleting the booking detail");
                            }

                            return "Cancellation";
                        } else {
                            //handling incorrect otp
                            model.addAttribute("errorMessage", "Invalid-OTP");
                            session.invalidate();
                            return "Cancellation";
                        }
                    } else {
                        session.removeAttribute("otp");
                        session.removeAttribute("otpCreationTime");
                        model.addAttribute("errorMessage", "OTP expired");
                        session.invalidate();
                        return "Cancellation";
                    }


                } else {
                    model.addAttribute("errorMessage", "An error occurred while cancelling the booking");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while cancelling the booking");
        }
        return "Cancellation";
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(9999);
        return otp;
    }
}
