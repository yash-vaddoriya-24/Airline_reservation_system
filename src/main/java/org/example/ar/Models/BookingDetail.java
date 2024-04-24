package org.example.ar.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Entity
@NoArgsConstructor
public class BookingDetail {

    @Id
    private String PNR;
    private String email;
    private String flightNo;
    private String username;
    private String dateOfFlight;
    private int noOfSeats;
    private String ticketType;
    private int adults;

    public BookingDetail(String PNR,String email,String flightNo, String username, String dateOfFlight, int noOfSeats, String ticketType, int adults) {
        this.PNR = PNR;
        this.email = email;
        this.flightNo = flightNo;
        this.username = username;
        this.dateOfFlight = dateOfFlight;
        this.noOfSeats = noOfSeats;
        this.ticketType = ticketType;
        this.adults = adults;
    }




}
