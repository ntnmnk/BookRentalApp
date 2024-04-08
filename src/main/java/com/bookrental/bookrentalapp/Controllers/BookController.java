package com.bookrental.bookrentalapp.Controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.bookrentalapp.Exceptions.BookNotFoundException;
import com.bookrental.bookrentalapp.Exceptions.BookRentalException;
import com.bookrental.bookrentalapp.Models.Book;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Services.BookService;
import com.bookrental.bookrentalapp.Services.UserService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);
    
    @Autowired
    UserService userService;

     @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody Book book,Authentication authentication) {
        try {
            logger.info("Attempting to add a new book");
            logger.debug("Book details: {}", book);
    
            Book newBook = bookService.addBook(book);
            logger.info("Book added successfully: {}", newBook.getTitle());
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while adding a new book", e);
            return new ResponseEntity<>("Failed to add book: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<String> rentBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // You can ignore the password as it's not typically available in the Authentication object
        
        logger.info("Renting book with ID {} for user with username {}", bookId, username);
        try {
            // Assuming you have a method to get the userId based on username
            Optional<User> userId = userService.findByUserName(username);
            bookService.rentBook(bookId, userId.get().getId());
            logger.info("Book rented successfully for user with username {}", username);
            return ResponseEntity.ok("Book rented successfully");
        } catch (BookRentalException e) {
            logger.error("Failed to rent book for user with username {}: {}", username, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }
    @PostMapping("/{userId}/{bookId}/return")
    public ResponseEntity<String> returnBook( @PathVariable Long userId,@PathVariable Long bookId) {
       
            try {
                logger.info("Returning book with ID {} for user with ID {}", bookId, userId);
                bookService.returnBook(userId, bookId);
                logger.info("Book returned successfully for user with ID {}", userId);
                return ResponseEntity.ok("Book returned successfully");
            } catch (BookRentalException e) {
                logger.error("Failed to return book for user with ID {}: {}", userId, e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }    
    

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            logger.info("Attempting to retrieve book with ID: {}", id);
            Book book = bookService.getBookById(id).get();
            logger.info("Book with ID {} retrieved successfully", id);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            logger.error("Book with ID {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}

