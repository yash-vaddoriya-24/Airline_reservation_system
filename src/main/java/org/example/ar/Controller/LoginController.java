package org.example.ar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.ar.EmailService.JavaSmtpGmailSenderApplication;
import org.example.ar.Models.CustomerDetail;
import org.example.ar.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {


    @Autowired
    CustomerService customerService;

    @Autowired
    JavaSmtpGmailSenderApplication javaSmtpGmailSenderApplication;

    @PostMapping("Login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        try {
            CustomerDetail customerDetail = customerService.validateLogin(username, password);
            if (customerDetail != null && customerDetail.isConfirmed()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("customerUsername", customerDetail.getUsername());
                session.setAttribute("customerEmail", customerDetail.getEmail());
                System.out.println(customerDetail.getUsername());
                return "index";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
            }
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while logging in");
            return "login";
        }
    }


    @PostMapping(value = "Register")
    public String register(HttpServletRequest request, Model model, @RequestParam String username, @RequestParam String password, @RequestParam String email_address, @RequestParam String fullname, @RequestParam String countryCode, @RequestParam String phno, @RequestParam String state, @RequestParam String city, @RequestParam String pincode) {
        try {

            List<CustomerDetail> customerDetail1 = customerService.findByEmail(email_address);
            if (customerDetail1 != null && !customerDetail1.isEmpty()) {
                long tokenCreationTime = customerDetail1.getFirst().getCreated().getTime();
                if (5 * 60 * 1000 > System.currentTimeMillis() - tokenCreationTime) {
                    if (!customerDetail1.getFirst().isConfirmed())
                        model.addAttribute("errorMessage", "An activation link for this email has already been sent. Please check your inbox and spam folder. If you haven't received it, please wait for a few minutes before requesting another activation link.");
                    else
                        model.addAttribute("errorMessage", "email address already in use");

                } else
                    model.addAttribute("errorMessage", "Email address already in use");

                return "register";
            }

            CustomerDetail customerDetail = new CustomerDetail(fullname, username, password, email_address, countryCode, phno, city, state, pincode);

            //Generate random token
            String token = UUID.randomUUID().toString();

            customerDetail.setToken(token);
            customerService.saveAllDetails(customerDetail);

            //Get the base token
            String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());

            //send the confirmation link
            String confirmationLink = baseUrl + "/confirmRegistration?token=" + token;
            String emailContent = "Hello" + username + ",\n\n" + "\"Thank you for registering with us!\n\"" + "Please click the following link to confirm your registration: " + "\n" + confirmationLink + "\n\n" + "This link is valid for 5 minutes only.\n\n"
                    + "If you did not register, please ignore this email.\n\n"
                    + "Best regards,\nAIRRESERVE";
            ;
            javaSmtpGmailSenderApplication.sendMail(email_address, "Confirm Registration", emailContent);

            model.addAttribute("errorMessage", "A confirmation email has been sent to your email address.");
            return "register";


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occured while processing your request");
            return "register";
        }
    }


    @GetMapping("confirmRegistration")
    public String confirmRegistration(@RequestParam String token, Model model) {
        // Find user by token
        CustomerDetail customerDetail = customerService.findByToken(token);

        if (customerDetail != null) {
            //verify or check the link is expired or not
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            long tokenCreationTime = customerDetail.getCreated().getTime();
            long currentTimeMillis = currentTime.getTime();
            long timeDifferenceMillis = currentTimeMillis - tokenCreationTime;
            long fiveMinutesMillis = 5 * 60 * 1000; // 5 minutes in milliseconds

            if (!customerDetail.isConfirmed() && timeDifferenceMillis <= fiveMinutesMillis) {

                customerDetail.setConfirmed(true);
                customerService.saveAllDetails(customerDetail);
                model.addAttribute("errorMessage", "Your registration is confirmed. You can now log in.");
            } else {
                model.addAttribute("errorMessage", "Invalid or expired confirmation link.");
                return "register";
            }
        } else {
            model.addAttribute("errorMessage", "Invalid or expired confirmation link.");
            return "register";
        }
        return "login";
    }

    @PostMapping("validateLogin")
    public String validateLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                if (session.getAttribute("customerUsername") != null) {
                    session.setAttribute("flightNo", request.getParameter("flightNo"));
                    return "redirect:/booking";
                } else {
                    return "redirect:/booking_error_msg";
                }
            }
            return "redirect:/booking_error_msg";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occured while processing your request");
            return "availability";
        }
    }

    @GetMapping("{fileName}")
    public String getHtmlPage(@PathVariable String fileName) {

        return fileName;
    }

}
