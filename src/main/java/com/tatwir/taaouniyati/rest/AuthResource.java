package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.AuthRequest;
import com.tatwir.taaouniyati.model.AuthResponse;
import com.tatwir.taaouniyati.service.AdminService;
import com.tatwir.taaouniyati.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;


    @PostMapping
    public AuthResponse LoginUser(@RequestBody AuthRequest authRequest){
        return authService.UserAuthentication(authRequest);

    }
}
