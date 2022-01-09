package com.marscolony.backoffice.controllers;

import com.marscolony.backoffice.dtos.JwtAuthResponse;
import com.marscolony.backoffice.dtos.LoginDto;
import com.marscolony.backoffice.dtos.SignUpDto;
import com.marscolony.backoffice.entities.User;
import com.marscolony.backoffice.repositories.RoleRepository;
import com.marscolony.backoffice.repositories.UserRepository;
import com.marscolony.backoffice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from token provider class
        String token = tokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        if(userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("email is already taken", HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        if (signUpDto.getAdmin() == true) {
            var roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
        } else{
            var roles = roleRepository.findByName("ROLE_USER").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
        }

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }

}
