package org.example.ar.Repository;


import org.example.ar.Models.FlightDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDetailsRepository extends JpaRepository<FlightDetails, Integer> {

    FlightDetails findByFlightNo(Long flightNo);

    List<FlightDetails> findByFromCityAndToCity(String fromCity, String toCity);

    Integer removeByFlightNo(Long flightNo);

    List<FlightDetails> findAll();

    FlightDetails save(FlightDetails flightDetails);

    @Modifying
    @Query("UPDATE FlightDetails fd SET  fd.flightName = :flightName, fd.fromCity = :fromCity,fd.toCity = :toCity,fd.dateOfFlight = :dateOfFlight,fd.flightArrivalTime = :flightArrivalTime WHERE fd.flightNo = :flightNo")
    Integer updateFlightDetailsByFlightNo(Long flightNo, String flightName, String fromCity, String toCity, String dateOfFlight, String flightArrivalTime);


}
