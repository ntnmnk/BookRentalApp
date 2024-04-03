package com.bookrental.bookrentalapp.Repositories;


import org.springframework.stereotype.Repository;

import com.bookrental.bookrentalapp.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    
}
