package com.formmanagement.service;

import com.formmanagement.dto.JwtResponse;
import com.formmanagement.dto.LoginRequest;
import com.formmanagement.dto.RegisterRequest;
import com.formmanagement.model.User;
import com.formmanagement.repository.UserRepository;
import com.formmanagement.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;
    
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        User userDetails = (User) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails);
    }
    
    public User registerUser(RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        
        // Create new user's account
        User user = new User(signUpRequest.getUsername(), 
                            signUpRequest.getEmail(),
                            encoder.encode(signUpRequest.getPassword()),
                            User.Role.USER);
        
        return userRepository.save(user);
    }
}
