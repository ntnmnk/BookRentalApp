package com.bookrental.bookrentalapp.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.bookrentalapp.Models.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
    Optional<User> findByuserName(String username); 
    
    Boolean existsByuserName(String username);
}
