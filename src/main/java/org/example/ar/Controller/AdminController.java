package org.example.ar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.ar.Models.FlightDetails;
import org.example.ar.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    FlightDetailsService flightDetailsService;


    @PostMapping("adminLogin")
    public String adminLogin(@RequestParam("admin_name") String username, @RequestParam("admin_pass") String password, HttpServletRequest request, Model model) {
        try {
            if (username.equals("adminUser") && password.equals("admin@123")) {
                HttpSession session = request.getSession(true);
                session.setAttribute("adminSuccess", "admin success");
                return "admin_login_pass";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "adminlogin";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while processing your request");
            return "adminlogin";
        }
    }

    @PostMapping("deleteFlightDetails")
    public String deleteFlightDetails(@RequestParam("flightId") String flightID, Model model, HttpSession session) {
        try {
            Long longFightID = Long.parseLong(flightID);
            if (flightDetailsService.getAllFlightDetailsById(longFightID) != null && session.getAttribute("adminSuccess") != null) {
                Integer rowAffected = flightDetailsService.DeleteFlightDetailsByNo(longFightID);

                //verify the row affected
                if (rowAffected > 0) {
                    session.setAttribute("deleteSuccess", "Flight Details deleted successfully");
                } else {
                    model.addAttribute("errorMessage", "Flight Details not found");
                }
            } else {
                model.addAttribute("errorMessage", "Flight details not found");
            }
            return "delflight";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while processing your request");
            return "admin_login_pass";
        }
    }

    @PostMapping("addFlight")
    public String addFlightDetails(@RequestParam("flight_no") Long flightNo, @RequestParam("flight_name") String flightName, @RequestParam("from_city") String fromCity, @RequestParam("to_city") String toCity, @RequestParam("date_of_flight") String dateOfFlight, @RequestParam("arrival_time") String flightArrivalTime, Model model, HttpSession session) {

        try {
            if (session.getAttribute("adminSuccess") != null) {
                FlightDetails flightDetails = new FlightDetails(flightNo, flightName, fromCity, toCity, dateOfFlight, flightArrivalTime);
                FlightDetails flightDetailsSaved = flightDetailsService.saveAllFlightDetails(flightDetails);
                if (flightDetailsSaved != null) {
                    model.addAttribute("errorMessage", "Flight Details added successfully");
                    return "addflights";
                }
                model.addAttribute("errorMessage", "An error occurred while processing your request");
                return "addflights";
            } else {
                model.addAttribute("errorMessage", "You are not logged in");
                return "adminlogin";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while processing your request");
            return "addflights";
        }

    }

    @PostMapping("updateFlightDetails")
    public String updateFlightDetails(@RequestParam("flight_no") Long flightNo, @RequestParam("flight_name") String flightName, @RequestParam("from_city") String fromCity, @RequestParam("to_city") String toCity, @RequestParam("date_of_flight") String dateOfFlight, @RequestParam("arrival_time") String flightArrivalTime, Model model, HttpSession session) {

        try {
            if (session.getAttribute("adminSuccess") != null) {
                FlightDetails flightDetails = new FlightDetails(flightNo, flightName, fromCity, toCity, dateOfFlight, flightArrivalTime);
                Integer rowAffected = flightDetailsService.updateFlightDetails(flightNo, flightName, fromCity, toCity, dateOfFlight, flightArrivalTime);
                if (rowAffected > 0) {
                    model.addAttribute("errorMessage", "Flight Details updated successfully");
                    return "updateflight";
                }
                model.addAttribute("errorMessage", "An error occurred while processing your request");
                return "updateflight";
            } else {
                model.addAttribute("errorMessage", "You are not logged in");
                return "adminlogin";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while processing your request");
            return "updateflight";
        }


    }
}
