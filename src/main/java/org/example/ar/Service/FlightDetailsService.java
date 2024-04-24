package org.example.ar.Service;

import org.example.ar.Models.FlightDetails;
import org.example.ar.Repository.FlightDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FlightDetailsService {

    @Autowired
    private FlightDetailsRepository flightDetailsRepository;


    public FlightDetails getAllFlightDetailsById(Long flightNo) {
        try {
            return flightDetailsRepository.findByFlightNo(flightNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<FlightDetails> getAllFlightDetailsByFromCityToCity(String fromCity, String toCity) {
        try {
            return flightDetailsRepository.findByFromCityAndToCity(fromCity, toCity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer DeleteFlightDetailsByNo(Long flightNo) {
        try {
            return flightDetailsRepository.removeByFlightNo(flightNo);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public FlightDetails saveAllFlightDetails(FlightDetails flightDetails) {
        try {
            return flightDetailsRepository.save(flightDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer updateFlightDetails(Long flightNo, String flightName, String fromCity, String toCity, String dateOfFlight, String flightArrivalTime) {
        try {
            return flightDetailsRepository.updateFlightDetailsByFlightNo(flightNo, flightName, fromCity, toCity, dateOfFlight, flightArrivalTime);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
