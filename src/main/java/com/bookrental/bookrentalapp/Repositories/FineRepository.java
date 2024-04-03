package com.bookrental.bookrentalapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.bookrentalapp.Models.Fine;

@Repository
public interface FineRepository extends JpaRepository<Fine,Long>{
    
}
