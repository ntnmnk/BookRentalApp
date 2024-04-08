package com.bookrental.bookrentalapp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  PasswordEncoder passwordEncoder;


  // @Autowired
  // private AuthEntryPointJwt unauthorizedHandler;

  // @Bean
  // public AuthTokenFilter authenticationJwtTokenFilter() {
  //   return new AuthTokenFilter();
  // }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable());

    httpSecurity.authenticationProvider(authenticationProvider());

     httpSecurity
     .csrf(csrf -> csrf.disable()) ;// Disables CSRF protection
     

httpSecurity
       .authorizeHttpRequests(configurer ->
         configurer
          .requestMatchers(   "/**"
        //  "/api/users/register",
        //      "/api/users",
        //      "/api/users/login","/api/auth/signup","/api/auth/signout","/api/auth/signin","/api/users/message"
          )
      .permitAll()
       .requestMatchers("/api/books/add").hasRole("ADMIN")
      .requestMatchers(HttpMethod.GET, "/api/books/all","/api/users/all").permitAll()
       .anyRequest()
       .authenticated()
      );

      httpSecurity.httpBasic(withDefaults())
      .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Enables HTTP Basic Authentication with default settings.

   
     //httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


    return httpSecurity.build();
  }

  @Bean
  AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }
}