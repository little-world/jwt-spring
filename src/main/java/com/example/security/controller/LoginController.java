package com.example.security.controller;


import com.example.security.config.TokenProvider;
import com.example.security.model.User;
import com.example.security.model.UserDTO;
import com.example.security.model.JWTResponse;
import com.example.security.service.RegisterService;
import com.example.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RegisterService registerService;

    @Autowired
    TokenProvider tokenProvider;


    @PostMapping("/login")
    public JWTResponse authenticateUser(@RequestBody UserDTO userDTO) throws Exception {
        Authentication auth = authenticate(userDTO.getUsername(), userDTO.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        String jwt = tokenProvider.doGenerateToken(userDetails.getUsername());
        return new JWTResponse(jwt, new UserDTO(userDetails.getUsername(), userDetails.getPassword()));
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User newUser = registerService.registerNewUser(userDTO);
        return new UserDTO(newUser.getUsername(), newUser.getPassword());
    }


    private Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        return authentication;
    }
}
