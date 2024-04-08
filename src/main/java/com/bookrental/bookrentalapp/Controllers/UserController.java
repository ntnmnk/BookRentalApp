package com.bookrental.bookrentalapp.Controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.bookrentalapp.Config.UserDetailsServiceImpl;
import com.bookrental.bookrentalapp.Exceptions.DuplicateResourceException;
import com.bookrental.bookrentalapp.Exceptions.UserNotFoundException;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Services.SignUpUserDto;
import com.bookrental.bookrentalapp.Services.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    //api/users/message  api/users/message
    @PostMapping("/ok")
    public ResponseEntity<?> getmessage() {
       return ResponseEntity.ok().body("Welcome");
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody User user) {
        try {
            logger.info("Received request to register user: {}", user.getEmail());
            User newUser = userService.registerUser(user);
            logger.info("User registered successfully: {}", newUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (DuplicateResourceException e) {
            logger.error("Failed to register user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        } catch (Exception e) {
            logger.error("An unexpected error occurred while registering user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }



    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        Optional<User> userOptional = userService.findByUserName(userName);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            logger.info("Received request to get all users.");
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()|| users==null) {
                logger.info("No users found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
            }
            logger.info("Returning {} users.", users.size());
            return ResponseEntity.ok(users);
        }  catch (UserNotFoundException e) {
            logger.info("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        }
        catch (Exception e) {
            logger.error("Error occurred while retrieving users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve users");
        }
    }


    


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        logger.info("Received login request for user with username: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails =userDetailsServiceImpl.loadUserByUsername(username);

            logger.info("User logged in successfully: {}", username);
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            logger.error("Login attempt failed for user with username: {}", username, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }
        @GetMapping("/{userId}")
        public ResponseEntity<?> getUserById(@PathVariable String userId) {
            try {
                Optional<User> user = userService.findByUserName(userId);
                return ResponseEntity.ok(user.get());
            } catch (ResourceNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("not found"); // or any error message you prefer
            }
        }
    
    }
