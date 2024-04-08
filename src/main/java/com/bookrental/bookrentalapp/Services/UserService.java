package com.bookrental.bookrentalapp.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookrental.bookrentalapp.Constants.Role;
import com.bookrental.bookrentalapp.Exceptions.DuplicateResourceException;
import com.bookrental.bookrentalapp.Exceptions.UserNotFoundException;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Repositories.UserRepository;


@Service
@Configuration
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    
    // public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    //     this.userRepository = userRepository;
    //     this.passwordEncoder = passwordEncoder;
    // }

    public User registerUser(User user) throws DuplicateResourceException {
        try {
            // Encrypt the password before storing
            user.setPassword(passwordEncoder.encode(user.getPassword()));
           
            // Default role to "User" if not specified
            if (user.getRoles().isEmpty()) {
                user.addRole(Role.USER);
            }
            
            // Set other properties
            user.setRentals(Collections.emptyList());
            user.setActive(true);
            user.setActiveRentals(0);
            
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Duplicate entry
            throw new DuplicateResourceException("User with email '" + user.getEmail() + "' already exists");
        } catch (Exception e) {
            // Other unexpected exceptions
            throw new RuntimeException("Failed to register user", e);
        }
    }
    


    public User loginUser(String email, String password) {
        logger.info("Attempting to login user with email: {}", email);
        
        User user = userRepository.findByuserName(email)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", email);
                    return new RuntimeException("User not found with email: " + email);
                });

        if (passwordEncoder.matches(password, user.getPassword())) {
            logger.info("Password matched for user with email: {}", email);

            // Manually authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

            // Set the authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("User successfully logged in: {}", email);
            return user;
        } else {
            logger.warn("Incorrect password for user with email: {}", email);
            throw new RuntimeException("Incorrect password");
        }
    }

    

    public List<User> getAllUsers() {
        logger.info("Fetching all users from the database.");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found in the database");
        }
        logger.info("Retrieved {} users from the database.", users.size());
        return users;

    }

    public User getByUserId(Long userId) {
        logger.info("Fetching user by user ID: {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User Id cannot be null!!!");
        }
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }


    public Optional<User> findByUserName(String userName) {
        return userRepository.findByuserName(userName);
    }

    public void deleteUser(Long userId) {
        logger.info("Deleting user by user ID: {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User Id cannot be null!!!");
        }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(Long.toString(userId)));
        userRepository.delete(user);
        logger.info("User deleted successfully");
    }

 
}