package org.example.ar.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String city;
    private String state;
    private String pincode;
    private String token;
    private boolean Confirmed;
    private Timestamp created;

    public CustomerDetail(String name, String username, String password, String email, String countryCode, String phoneNumber, String city, String state, String pincode) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.Confirmed = false;
        this.created = new Timestamp(System.currentTimeMillis());
    }
}
