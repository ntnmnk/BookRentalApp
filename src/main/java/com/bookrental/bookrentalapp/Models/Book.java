package com.bookrental.bookrentalapp.Models;


import java.util.HashSet;
import java.util.Set;

import com.bookrental.bookrentalapp.Constants.AvailabilityStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String ISBN;

    private String title;
    
    
    private String author;
    
    private String genre;
    
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @OneToMany(mappedBy = "book")
    private Set<Rental> rentals = new HashSet<>(0);
    
    // Getters and setters
}

