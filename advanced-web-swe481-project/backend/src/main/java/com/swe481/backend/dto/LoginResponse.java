package com.swe481.backend.dto;

public class LoginResponse {
    private String status;
    private String message;
    private Integer customerId; // Sending ID for now, JWT later if needed

    public LoginResponse() {
    }

    public LoginResponse(String status, String message, Integer customerId) {
        this.status = status;
        this.message = message;
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
