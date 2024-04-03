package com.bookrental.bookrentalapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookrental.bookrentalapp.Models.Fine;
import com.bookrental.bookrentalapp.Repositories.FineRepository;

@Service
public class FineService {
       @Autowired
    private FineRepository fineRepository;

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }
 
}
