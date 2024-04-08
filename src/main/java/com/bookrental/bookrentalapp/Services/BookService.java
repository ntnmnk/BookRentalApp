package com.bookrental.bookrentalapp.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookrental.bookrentalapp.Constants.AvailabilityStatus;
import com.bookrental.bookrentalapp.Exceptions.BookNotFoundException;
import com.bookrental.bookrentalapp.Exceptions.BookRentalException;
import com.bookrental.bookrentalapp.Exceptions.UserNotFoundException;
import com.bookrental.bookrentalapp.Models.Book;
import com.bookrental.bookrentalapp.Models.Rental;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Repositories.BookRepository;
import com.bookrental.bookrentalapp.Repositories.UserRepository;

@Service
public class BookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void rentBook(Long bookId, Long userId) {
        if (userId != null) {
            logger.info("Renting book with ID: {} for user with ID: {}", bookId, userId);

            Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", bookId);
                    return new BookNotFoundException("Book not found with ID: " + bookId);
                });

            User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException("User not found with ID: " + userId);
                });

            // Check if the user already has two active rentals
            if (user.getActiveRentals() >= 2) {
                logger.error("User with ID {} has reached the maximum number of active rentals", userId);
                throw new BookRentalException("User has reached the maximum number of active rentals");
            }

            // Rent the book
            if (book.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE) {
                Rental rental = new Rental(user, book);
                rental.setRentalDate(LocalDate.now());
                book.setAvailabilityStatus(AvailabilityStatus.RENTED);
                book.getRentals().add(rental);
                user.getRentals().add(rental);
                user.setActiveRentals(user.getActiveRentals()+(3-2));
                // Save changes to the database
                bookRepository.save(book);
                userRepository.save(user);
                logger.info("Book rented successfully");
            } else {
                logger.error("Book with ID {} is not available for rental", bookId);
                throw new BookRentalException("Book is not available for rental.");
            }
        } else {
            logger.error("User ID is null");
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
    }

    public Book findBooksByName(String title) {
        return bookRepository.findBytitle(title);
    }


    public  Optional <Book> getBookById(Long id) {
        logger.info("Fetching book with id: " + id);
        return bookRepository.findById(id);
    }


    


    public void returnBook(Long bookId, Long userId) {
        Optional<Book> bookOptional   = bookRepository.findById(bookId);

                if (bookOptional.isPresent()) {
                    Book book = bookOptional.get();
        
                    Optional<User> userOptional = userRepository.findById(userId);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
        
                        // Return the book
                        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
                        bookRepository.save(book);
        
                        // Update user's active rentals count
                        user.setActiveRentals(user.getActiveRentals() - 1);
                        userRepository.save(user);
                    } else {
                        throw new UserNotFoundException("User not found with id: " + userId);
                    }
                } else {
                    throw new BookNotFoundException("Book not found with id: " + bookId);
                }
            }
}

