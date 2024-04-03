package com.bookrental.bookrentalapp.Controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.bookrentalapp.Exceptions.BookRentalException;
import com.bookrental.bookrentalapp.Models.Book;
import com.bookrental.bookrentalapp.Services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);


     @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        logger.info("Attempting to add a new book: {}", book.getTitle());
        Book newBook = bookService.addBook(book);
        logger.info("Book added successfully: {}", newBook.getTitle());
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }


    @PostMapping("/{bookId}/rent")
    public ResponseEntity<String> rentBook(@PathVariable Long bookId, @RequestBody Long userId) {
        if (userId != null) {
            logger.info("Renting book with ID {} for user with ID {}", bookId, userId);
            try {
                bookService.rentBook(bookId, userId);
                logger.info("Book rented successfully for user with ID {}", userId);
                return ResponseEntity.ok("Book rented successfully");
            } catch (BookRentalException e) {
                logger.error("Failed to rent book for user with ID {}: {}", userId, e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            logger.error("User ID is null or empty");
            return ResponseEntity.badRequest().body("User ID cannot be null or empty");
        }
    }

}


