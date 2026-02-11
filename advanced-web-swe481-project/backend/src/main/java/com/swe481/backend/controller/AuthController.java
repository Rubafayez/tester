package com.swe481.backend.controller;

import com.swe481.backend.dto.LoginRequest;
import com.swe481.backend.dto.LoginResponse;
import com.swe481.backend.entity.Customer;
import com.swe481.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class AuthController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Authenticates a customer using email and password.
     * Checks credentials against the database and establishes a session.
     *
     * @param loginRequest The login request containing email and password.
     * @param session      The HTTP session to store the authenticated customer ID.
     * @return ResponseEntity containing the login response with status and customer
     *         ID.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(loginRequest.getEmail());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(loginRequest.getPassword())) {
                session.setAttribute("customerId", customer.getId());
                return ResponseEntity.ok(new LoginResponse("success", "Login successful", customer.getId()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("fail", "Invalid email or password", null));
    }
}
