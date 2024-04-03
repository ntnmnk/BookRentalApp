package com.bookrental.bookrentalapp.Controllers;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginRequest {
    private String userName;
    private String password; 

}
