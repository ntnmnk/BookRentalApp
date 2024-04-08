// package com.bookrental.bookrentalapp.jwt;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;


// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.ResponseCookie;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;
// import org.springframework.web.util.WebUtils;

// import com.bookrental.bookrentalapp.Config.CustomUserDetailsService;
// import com.bookrental.bookrentalapp.Config.UserDetailsServiceImpl;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// import java.util.function.Function;


// @Component
// public class JwtUtils {
    
//     private static final Logger logger = LogManager.getLogger(JwtUtils.class);
//     @Value("${security.jwt.secret-key}")
//     private String secretKey;

//     @Value("${security.jwt.expiration-time}")
//     private long jwtExpiration;

//     public String extractUsername(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         final Claims claims = extractAllClaims(token);
//         return claimsResolver.apply(claims);
//     }

//     public String generateToken(UserDetails userDetails) {
//         return generateToken(new HashMap<>(), userDetails);
//     }

//     public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//         return buildToken(extraClaims, userDetails, jwtExpiration);
//     }

//     public long getExpirationTime() {
//         return jwtExpiration;
//     }

//     private String buildToken(
//             Map<String, Object> extraClaims,
//             UserDetails userDetails,
//             long expiration
//     ) {
//         return Jwts
//                 .builder()
//                 .setClaims(extraClaims)
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         final String username = extractUsername(token);
//         return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return extractExpiration(token).before(new Date());
//     }

//     private Date extractExpiration(String token) {
//         return extractClaim(token, Claims::getExpiration);
//     }

//     private Claims extractAllClaims(String token) {
//         return Jwts
//                 .parserBuilder()
//                 .setSigningKey(getSignInKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }

//     private Key getSignInKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }
// }