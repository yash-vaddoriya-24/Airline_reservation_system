package org.example.ar.Controller;


import org.example.ar.Models.FlightDetails;
import org.example.ar.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightDetailsController {

    @Autowired
    FlightDetailsService flightDetailsService;

    @GetMapping("/flightDetails")
    public ResponseEntity<?> findFlighDetailsByNo(Model model, @RequestParam Long flightNo) {
        try {
            FlightDetails flightDetails = flightDetailsService.getAllFlightDetailsById(flightNo);
            if (flightDetails != null) {
                return new ResponseEntity<>(flightDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while fetching data.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

