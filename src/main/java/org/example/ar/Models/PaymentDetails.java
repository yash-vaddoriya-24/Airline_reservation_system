package org.example.ar.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PaymentDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PNR;
    private String transactionId;
    private String address;
    private String paymentGateway;
    private String paymentId;
    private int fair;
    private String coupon;
    private int discount;
    private int actualFair;

    public PaymentDetails(Long PNR, String transactionId, String address, String paymentGateway, String paymentId, int fair, String coupon, int discount, int actualFair) {
        this.PNR = PNR;
        this.transactionId = transactionId;
        this.address = address;
        this.paymentGateway = paymentGateway;
        this.paymentId = paymentId;
        this.fair = fair;
        this.coupon = coupon;
        this.discount = discount;
        this.actualFair = actualFair;
    }

}
