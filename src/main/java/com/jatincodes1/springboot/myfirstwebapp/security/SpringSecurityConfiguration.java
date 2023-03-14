package com.jatincodes1.springboot.myfirstwebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
    /**
     * when to store password and username use LDAP or Database
     * Here we will use In Memory to store password and username
     *
     * Current features :
     * 1. all urls are protected
     * 2. a login form is shown for unauthorized requests
     * 3. CSRF { cross site request Forgery } is disabled to make H2 work
     * 4. H2 use Frames , but spring security does not allow it .. so have enabled use of Frames
     *
     */

    //InMemoryUserDetailsManager
    //InMemoryUserDetailsManager(UserDetails... users)  // see the class to explore more functionality
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager(){

        UserDetails userDetails1 = createNewUser("in28minutes", "dummy");
        UserDetails userDetails2 = createNewUser("Jatin", "dummy");

        return new InMemoryUserDetailsManager(userDetails1,userDetails2); // it takes variable arguments
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input); // in this passwordEncoder() is defined by us .
        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)  // it is a predefined User.class function
                .username(username)
                .password(password)
                .roles("USER","ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()); // this is use to authorize request
        http.formLogin(withDefaults()); // if not a authorise user .. then send to page which collect username and password
        // now do what is needed
        http.csrf().disable();  // disable csrf
        http.headers().frameOptions().disable(); // enable frames

        return http.build();
    }




}
