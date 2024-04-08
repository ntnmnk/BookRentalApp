package com.bookrental.bookrentalapp.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookrental.bookrentalapp.Constants.AvailabilityStatus;
import com.bookrental.bookrentalapp.Models.Book;
import com.bookrental.bookrentalapp.Models.Rental;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Repositories.BookRepository;
import com.bookrental.bookrentalapp.Repositories.RentalRepository;
import com.bookrental.bookrentalapp.Repositories.UserRepository;

@Service
public class RentalService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;


        public List<Rental> getAllRentalsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRentals();
    }

        public void rentBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for rental");
        }
        Rental rental = new Rental(user, book);
        rental.setRentalDate(LocalDate.now());
        rentalRepository.save(rental);
        book.setAvailabilityStatus(AvailabilityStatus.RENTED);
        bookRepository.save(book);
        user.setActiveRentals(user.getActiveRentals() + 1);
        userRepository.save(user);
    }


    
}
