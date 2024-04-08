// package com.bookrental.bookrentalapp.Controllers;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseCookie;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// import com.bookrental.bookrentalapp.Config.CustomUserDetailsService;
// import com.bookrental.bookrentalapp.Exceptions.DuplicateResourceException;
// import com.bookrental.bookrentalapp.Models.User;
// import com.bookrental.bookrentalapp.Repositories.UserRepository;
// import com.bookrental.bookrentalapp.Services.UserService;
// import com.bookrental.bookrentalapp.jwt.JwtUtils;

// @CrossOrigin(origins = "*", maxAge = 3600)
// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {
//     private static final Logger logger = LogManager.getLogger(AuthController.class);
//     @Autowired
//     AuthenticationManager authenticationManager;

//      @Autowired
//     private UserService userService;

//     @Autowired
//     UserRepository userRepository;

//     @Autowired
//     PasswordEncoder encoder;

//     @Autowired
//     JwtUtils jwtUtils;

//     @PostMapping("/signin")
//     public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

//         String username = loginRequest.getUserName();
//         String password = loginRequest.getPassword();
//         logger.info("Received login request for user with username: {}", username);

//         Authentication authentication = authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(username, password));

//     SecurityContextHolder.getContext().setAuthentication(authentication);

//         CustomUserDetailsService userDetails = (CustomUserDetailsService) authentication.getPrincipal();

//         String jwtToken = jwtUtils.generateToken(userDetails);
//         LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtUtils.getExpirationTime());

//         return ResponseEntity.ok(loginResponse);

//     }

//    @PostMapping("/signup")
//     public ResponseEntity<?> registerUser(@RequestBody User user) {
//         try {
//             logger.info("Received request to register user: {}", user.getEmail());
//             User newUser = userService.registerUser(user);
//             logger.info("User registered successfully: {}", newUser.getEmail());
//             return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//         } catch (DuplicateResourceException e) {
//             logger.error("Failed to register user: {}", e.getMessage());
//             return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//         } catch (Exception e) {
//             logger.error("An unexpected error occurred while registering user", e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exists");
//         }
//     }

//     // @PostMapping("/signout")
//     // public ResponseEntity<?> logoutUser() {
//     //     ResponseCookie cookie = jwtUtils.();
//     //     return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//     //             .body(new MessageResponse("You've been signed out!"));
//     // }
// }
