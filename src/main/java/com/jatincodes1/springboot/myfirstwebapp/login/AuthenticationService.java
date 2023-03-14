/***
 *
 * NO need of this file.
 * After adding Spring Security.
 *
 */



package com.jatincodes1.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean authenticate(String username, String password) {

        boolean isValidUserName = username.equalsIgnoreCase("in28minutes");
        boolean isValidPassword = password.equalsIgnoreCase("dummy");

        return isValidUserName && isValidPassword;
    }
}
