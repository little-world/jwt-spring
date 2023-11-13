package com.example.security.controller;


import com.example.security.config.JwtUtil;
import com.example.security.model.User;
import com.example.security.model.UserDTO;
import com.example.security.model.JWTResponse;
import com.example.security.service.RegisterService;
import com.example.security.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class LoginController {
    AuthenticationManager authenticationManager;
    UserDetailsServiceImpl userDetailsService;
    RegisterService registerService;
    JwtUtil tokenProvider;

    @PostMapping("/login")
    public JWTResponse authenticateUser(@RequestBody UserDTO userDTO) throws Exception {
        Authentication auth = authenticate(userDTO.getUsername(), userDTO.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        String jwt = tokenProvider.generateToken(userDetails.getUsername());
        return new JWTResponse(jwt, new UserDTO(userDetails.getUsername(), userDetails.getPassword()));
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User newUser = registerService.registerNewUser(userDTO);
        System.out.println("user id " + newUser.getId());
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
