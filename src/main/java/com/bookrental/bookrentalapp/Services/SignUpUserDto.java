package com.bookrental.bookrentalapp.Services;


import com.bookrental.bookrentalapp.Constants.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpUserDto {


    
    private String username;
   
    String firstname;

    String lastname;

    
    
    String email;

    
    
    
    String password;

   
    Role role;
    
    private int activeRentals;


    
}
