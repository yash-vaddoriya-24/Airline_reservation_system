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
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;
    private String email;
    private boolean Confirmed;
    private Timestamp created;

    public ForgotPasswordDetails(String token, String email) {
        this.token = token;
        this.email = email;
        this.Confirmed = false;
        this.created = new Timestamp(System.currentTimeMillis());
    }



}