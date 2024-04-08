package com.bookrental.bookrentalapp;

import com.bookrental.bookrentalapp.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BookrentalappApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookrentalappApplication.class, args);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
