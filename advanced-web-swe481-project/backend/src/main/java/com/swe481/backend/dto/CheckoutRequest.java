package com.swe481.backend.dto;

import java.time.LocalDate;

/**
 * DTO for checkout payment submission.
 *
 * Fields required by the Payment page:
 * - firstName: Cardholder first name
 * - lastName: Cardholder last name
 * - cardNumber: Credit card number
 * - expiration: Credit card expiration date
 *
 * Note (API contract): when sent as JSON, LocalDate is expected in ISO-8601 format: "YYYY-MM-DD".
 */
public class CheckoutRequest {

    private String firstName;
    private String lastName;
    private String cardNumber;
    private LocalDate expiration;

    /**
     * Default constructor required for JSON deserialization.
     */
    public CheckoutRequest() {
    }

    /**
     * Creates a new CheckoutRequest DTO.
     *
     * @param firstName  Cardholder first name
     * @param lastName   Cardholder last name
     * @param cardNumber Credit card number
     * @param expiration Credit card expiration date
     */
    public CheckoutRequest(String firstName, String lastName, String cardNumber, LocalDate expiration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
    }

    /**
     * @return Cardholder first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName Cardholder first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Cardholder last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName Cardholder last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Credit card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber Credit card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return Credit card expiration date
     */
    public LocalDate getExpiration() {
        return expiration;
    }

    /**
     * @param expiration Credit card expiration date
     */
    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }
}

