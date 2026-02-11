package com.swe481.backend.dto;

import java.time.LocalDate;

public class CheckoutRequest {
    private String firstName;
    private String lastName;
    private String cardNumber;
    private LocalDate expiration;

    public CheckoutRequest() {
    }

    public CheckoutRequest(String firstName, String lastName, String cardNumber, LocalDate expiration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }
}
