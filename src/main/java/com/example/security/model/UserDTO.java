package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;import lombok.AllArgsConstructor;import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
