package org.example.ar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.ar.EmailService.JavaSmtpGmailSenderApplication;
import org.example.ar.Models.CustomerDetail;
import org.example.ar.Models.ForgotPasswordDetails;
import org.example.ar.Service.CustomerService;
import org.example.ar.Service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
public class ResetPassword {

    @Autowired
    CustomerService customerService;

    @Autowired
    JavaSmtpGmailSenderApplication javaSmtpGmailSenderApplication;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("ResetPassword")
    public String resetPassword(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        try {
            if (email == null || email.isEmpty() || !isValidEmail(email)) {
                model.addAttribute("error", "Invalid email");
                return "PasswordReset";
            }

            List<CustomerDetail> customerDetail = customerService.findByEmail(email);
            if (customerDetail != null && !customerDetail.isEmpty()) {

                //start the session
                HttpSession session = request.getSession(true);
                session.setAttribute("ResetPasswordEmail", email);

                //store creation time
                long tokenCreationTime = System.currentTimeMillis();

                //Generate random token
                String token = UUID.randomUUID().toString();

                //Get the base token
                String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());

                String confirmationLink = baseUrl + "/SecureResetPassword?token=" + token;

                ForgotPasswordDetails forgotPasswordDetails = new ForgotPasswordDetails(token, email);

                //store the token and email
                forgotPasswordService.saveDetails(forgotPasswordDetails);

                //message string
                String subject = "Password Reset Request for Your Account";
                String body = "Dear, " + customerDetail.get(0).getUsername() + "\n" +
                        "\n" +
                        "You are receiving this email because a request has been made to reset the password for your AIRRESERVE account. If you did not request this change, please disregard this email.To proceed with the password reset, please click on the following link:\n" +
                        "\n" +
                        confirmationLink + "\n\n" + "Please note that this link is valid for a limited time only and should be used immediately.\n" +
                        "\n" +

                        "\n" +
                        "If you did not initiate this request, we recommend reviewing the security of your account and taking appropriate measures to ensure its safety.\n" +
                        "\n" +
                        "Thank you for choosing AIRRESERVE.\n" +
                        "\n" +
                        "Sincerely,\n" +
                        "AIRRESERVE Team";
                javaSmtpGmailSenderApplication.sendMail(email, subject, body);
                session.setAttribute("tokenCreationTime", tokenCreationTime);
                model.addAttribute("errorMessage", "Request password reset link sent to your registered email");
                return "PasswordReset";

            } else {
                model.addAttribute("errorMessage", "Email does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while trying to reset the password");
        }
        return "PasswordReset";
    }

    @GetMapping("SecureResetPassword")
    public String secureResetPassword(@RequestParam("token") String token, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            model.addAttribute("errorMessage", "Invalid or expired link");
            return "PasswordReset";
        }
        try {
            if (session.getAttribute("ResetPasswordEmail") != null) {
                long tokenCreationTime = (long) session.getAttribute("tokenCreationTime");
                long currentTime = System.currentTimeMillis();

                ForgotPasswordDetails forgotPasswordDetails = forgotPasswordService.findForgotPasswordDetailsBytoken(token);

                if (currentTime - tokenCreationTime < 5 * 60 * 1000 && (forgotPasswordDetails != null && !forgotPasswordDetails.isConfirmed())) {
                    session.setAttribute("token", token);
                    return "ResetPasswordSecure";
                } else {
                    model.addAttribute("errorMessage", "Invalid or expired link");
                    session.invalidate();
                    return "PasswordReset";
                }
            } else {
                model.addAttribute("errorMessage", "Invalid or expired link");
                return "PasswordReset";
            }


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while trying to reset the password");
        }
        return "PasswordReset";
    }

    @PostMapping("resetPasswordSecurely")
    public String forgotPassword(@RequestParam String email, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("password") String password, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                //password and confirm password matching
                if (!password.equals(confirmPassword)) {
                    model.addAttribute("errorMessage", "New password and confirm password must be same");
                    return "ResetPasswordSecure";
                }

                //current time and stored time
                long currentTime = System.currentTimeMillis();
                long tokenCreationTime = (long) session.getAttribute("tokenCreationTime");
                String token = (String) session.getAttribute("token");

                ForgotPasswordDetails forgotPasswordDetails = forgotPasswordService.findForgotPasswordDetailsBytoken(token);


                if (session.getAttribute("ResetPasswordEmail") != null && (currentTime - tokenCreationTime < 10 * 60 * 1000 && (!forgotPasswordDetails.isConfirmed()))) {
                    Integer rowAffected = customerService.updateCustomerDetail(email, password);

                    if (rowAffected > 0) {
                        forgotPasswordDetails.setConfirmed(true);
                        forgotPasswordService.saveDetails(forgotPasswordDetails);
                        model.addAttribute("errorMessage", "Your password has been reset, Now you can log in");
                        session.invalidate();
                        return "login";
                    } else {
                        model.addAttribute("errorMessage", "An error occurred while processing your request");
                        return "ResetPasswordSecure";
                    }
                } else {
                    model.addAttribute("errorMessage", "Session expired");
                    session.invalidate();
                    return "PasswordReset";
                }
            } catch (Exception e) {
                session.invalidate();
                model.addAttribute("errorMessage", "An error occurred while trying to reset the password");
                return "PasswordReset";
            }
        } else {
            model.addAttribute("errorMessage", "Session expired");
            return "PasswordReset";
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // Generate random number between 1000 and 9999
        return otp;
    }
}
