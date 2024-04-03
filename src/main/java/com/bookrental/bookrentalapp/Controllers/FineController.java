package com.bookrental.bookrentalapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.bookrentalapp.Models.Fine;
import com.bookrental.bookrentalapp.Services.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {
      @Autowired
    private FineService fineService;

    @GetMapping
    public ResponseEntity<List<Fine>> getAllFines() {
        List<Fine> fines = fineService.getAllFines();
        return new ResponseEntity<>(fines, HttpStatus.OK);
    }

  
}
