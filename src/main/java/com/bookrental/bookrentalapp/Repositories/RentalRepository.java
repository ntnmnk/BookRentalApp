package com.bookrental.bookrentalapp.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.bookrentalapp.Models.Book;
import com.bookrental.bookrentalapp.Models.Rental;
import com.bookrental.bookrentalapp.Models.User;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long>{

   // Optional<List<Rental>> findByUserIdAndActiveStatusTrue(User user); 


   // Optional<List<Rental>> findByUserId(User user);


   // Optional<Rental> findByUserIdAndBookIdAndActiveStatusTrue(User user, Book book); 
}
