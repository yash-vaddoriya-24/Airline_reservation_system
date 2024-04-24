package org.example.ar.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FlightDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long flightNo;
    private String flightName;
    private String fromCity;
    private String toCity;
    private String dateOfFlight;
    private String flightArrivalTime;

    public FlightDetails(Long flightNo, String flightName, String fromCity, String toCity, String dateOfFlight, String flightArrivalTime) {
        this.flightNo = flightNo;
        this.flightName = flightName;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.dateOfFlight = dateOfFlight;
        this.flightArrivalTime = flightArrivalTime;
    }
}
