package org.example.ar.Controller;

import org.example.ar.Models.FlightDetails;
import org.example.ar.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlightDetailsByField {

    @Autowired
    FlightDetailsService flightDetailsService;

    @GetMapping("/flightDetailsByCity")
    public String findFlightDetailsByCity(Model model, @RequestParam String from_city, @RequestParam String to_city) {
        try {
            List<FlightDetails> flightDetails = flightDetailsService.getAllFlightDetailsByFromCityToCity(from_city, to_city);
            if (flightDetails != null && !flightDetails.isEmpty()) {
                model.addAttribute("flightDetails", flightDetails);
            } else {
                model.addAttribute("message", "No flights found for the provided cities.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error occurred while fetching data.");
        }
        return "availability"; // Thymeleaf template name
    }
}

