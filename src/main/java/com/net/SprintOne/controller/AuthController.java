package com.net.SprintOne.controller;

import com.net.SprintOne.dtos.AuthenticationRequest;
import com.net.SprintOne.dtos.AuthenticationResponse;
import com.net.SprintOne.service.JwtUserDetailsService;
import com.net.SprintOne.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtTokenUtils;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return createAuthenticationToken(request);
    }

    private ResponseEntity<?> createAuthenticationToken(AuthenticationRequest request) {
        String encodedPassword = request.getPassword();
//        Authentication authentication = null;

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), encodedPassword)
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new BadCredentialsException("Incorrect username or password")
                    , HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getUsername());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtTokenUtils.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }
}
