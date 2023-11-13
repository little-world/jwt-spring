package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class JWTResponse {
    String token;
    UserDTO user;
}
