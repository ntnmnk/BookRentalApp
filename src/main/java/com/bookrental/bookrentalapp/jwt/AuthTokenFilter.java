// package com.bookrental.bookrentalapp.jwt;

// import java.io.IOException;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.bookrental.bookrentalapp.Config.UserDetailsServiceImpl;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.NoArgsConstructor;

// import org.springframework.security.core.Authentication;
// import org.springframework.web.servlet.HandlerExceptionResolver;



// @NoArgsConstructor
// public class AuthTokenFilter extends OncePerRequestFilter {

//     private  HandlerExceptionResolver handlerExceptionResolver;
//     @Autowired
//     private JwtUtils jwtUtils;
  
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;


//     public AuthTokenFilter(HandlerExceptionResolver handlerExceptionResolver){
//         this.handlerExceptionResolver = handlerExceptionResolver;
//     }
  
//     private static final Logger logger = LogManager.getLogger(AuthTokenFilter.class);
  
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//         throws ServletException, IOException {
//             final String authHeader = request.getHeader("Authorization");

//             if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                 filterChain.doFilter(request, response);
//                 return;
//             }
    
//             try {
//                 final String jwt = authHeader.substring(7);
//                 final String userEmail = jwtUtils.extractUsername(jwt);
    
//                 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
//                 if (userEmail != null && authentication == null) {
//                     UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
    
//                     if (jwtUtils.isTokenValid(jwt, userDetails)) {
//                         UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                                 userDetails,
//                                 null,
//                                 userDetails.getAuthorities()
//                         );
    
//                         authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                         SecurityContextHolder.getContext().setAuthentication(authToken);
//                     }
//                 }
    
//                 filterChain.doFilter(request, response);
//             } catch (Exception exception) {
//                 handlerExceptionResolver.resolveException(request, response, null, exception);
//             }
//         }
    
//         }        
