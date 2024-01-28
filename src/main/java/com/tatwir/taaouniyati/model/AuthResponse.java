package com.tatwir.taaouniyati.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String username;
    private String lastname;
    private String firstname;
    private List<String> roles;
}
