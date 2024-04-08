package com.bookrental.bookrentalapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.bookrentalapp.Models.Rental;
import com.bookrental.bookrentalapp.Services.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getAllRentalsForUser(@PathVariable Long userId) {
        try {
            List<Rental> rentals = rentalService.getAllRentalsForUser(userId);
            return ResponseEntity.ok(rentals);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

        @PostMapping("/rent/{userId}/{bookId}")
    public ResponseEntity<String> rentBook(@PathVariable Long userId, @PathVariable Long bookId) {
        try {
            rentalService.rentBook(userId, bookId);
            return ResponseEntity.ok("Book rented successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




    
}
