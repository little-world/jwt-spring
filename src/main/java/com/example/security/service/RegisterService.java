package com.example.security.service;

import com.example.security.model.User;
import com.example.security.model.UserDTO;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public User registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles("ROLE_USER");

        return userRepository.save(user);
    }
}
