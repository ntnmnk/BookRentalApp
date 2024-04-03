package com.bookrental.bookrentalapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.bookrentalapp.Models.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long>{
    
}
