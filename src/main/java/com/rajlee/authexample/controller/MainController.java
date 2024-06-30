package com.rajlee.authexample.controller;

import com.rajlee.authexample.service.MyUserDetailsService;
import com.rajlee.authexample.webtoken.JwtService;
import com.rajlee.authexample.webtoken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private AuthenticationManager authenticationManager; //help us to authenticate with username and password
    // no need to write logic seperately
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @GetMapping("/home")
    public String handleWelcome() {
        return "Home Page";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "Admin Page";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "User Page";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm){
       Authentication authentication= authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(),loginForm.password()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(myUserDetailsService.loadUserByUsername(loginForm.username()));
        }else{
            throw new UsernameNotFoundException("Invlaid credentials");
        }
    }
}
