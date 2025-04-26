//package com.expensetracker.service;
//
//import com.expensetracker.entity.User;
//import com.expensetracker.repository.UserRepository;
//import com.expensetracker.security.JwtUtil;
//import com.expensetracker.dto.LoginRequest;
//import com.expensetracker.dto.RegisterRequest;  // Update with correct DTO
//import com.expensetracker.dto.AuthResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public String register(RegisterRequest request) {
//        if (userRepository.existsByEmail(request.getEmail())) {
//            return "Email already exists!";
//        }
//
//        User user = new User();
//        user.setFullName(request.getFullName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword()); // Hash this using BCrypt in a real app
//        user.setRole("USER");
//
//        userRepository.save(user);
//        return "User registered successfully!";
//    }
//
//    // 🔐 Authenticate User
//    public AuthResponse login(LoginRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//
//        String token = jwtUtil.generateToken(userDetails);
//
//        return new AuthResponse(token);
//    }
//}


package com.expensetracker.service;

import com.expensetracker.entity.User;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.JwtUtil;
import com.expensetracker.dto.LoginRequest;
import com.expensetracker.dto.RegisterRequest;  // Update with correct DTO
import com.expensetracker.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Auto-wired BCryptPasswordEncoder

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists!";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt encoding
        user.setRole("USER");

        userRepository.save(user);
        return "User registered successfully!";
    }



//    // 🔐 Handle user authentication
//    public AuthResponse login(LoginRequest request) {
//        // Authenticate the user based on email and password
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        // Load user details
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//
//        // Generate a JWT token for the authenticated user
//        String token = jwtUtil.generateToken(userDetails);
//
//        return new AuthResponse(token);  // Return the generated token in the response
//    }
public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

    com.expensetracker.entity.User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtUtil.generateToken(userDetails, user.getId());

    return new AuthResponse(token);
}



}
