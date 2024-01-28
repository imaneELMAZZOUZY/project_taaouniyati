package com.tatwir.taaouniyati.service;


import com.tatwir.taaouniyati.config.UserInfoDetailsService;
import com.tatwir.taaouniyati.config.UserInfoUserDetails;
import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.model.AuthRequest;
import com.tatwir.taaouniyati.model.AuthResponse;
import com.tatwir.taaouniyati.repos.AdminRepository;
import com.tatwir.taaouniyati.repos.ClientRepository;
import com.tatwir.taaouniyati.repos.CooperativeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AuthService {

    private final ClientRepository clientRepository;
    private  final AdminRepository adminRepository;
    private final CooperativeRepository cooperativeRepository;
   private final AuthenticationManager authenticationManager;
    private final UserInfoDetailsService userInfoDetailsService;


    public AuthService(ClientRepository clientRepository,
                       AdminRepository adminRepository,
                       CooperativeRepository cooperativeRepository,
                       AuthenticationManager authenticationManager,
                       UserInfoDetailsService userInfoDetailsService) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.cooperativeRepository=cooperativeRepository;
        this.authenticationManager = authenticationManager;
        this.userInfoDetailsService = userInfoDetailsService;


    }

    public AuthResponse UserAuthentication(AuthRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserInfoUserDetails user = (UserInfoUserDetails) userInfoDetailsService.loadUserByUsername(authRequest.getUsername());

            AuthResponse authResponse = new AuthResponse(user.getUsername(),null,null,null);
            List<String> roles = new ArrayList<>();
            for(GrantedAuthority authority : user.getAuthorities()){
                String role= authority.toString();
                roles.add(role);

            }
            Client client= clientRepository.findByEmail(authResponse.getUsername()).orElse(null);
            if(client!=null)
            {
                authResponse.setRoles(roles);
                authResponse.setFirstname(client.getPrenom());
                authResponse.setLastname(client.getNom());
                authResponse.setUsername(client.getEmail());
                return authResponse;
            }

            Cooperative cooperative= cooperativeRepository.findByEmail(authResponse.getUsername()).orElse(null);
            if(cooperative!=null)
            {
                authResponse.setRoles(roles);
                authResponse.setLastname(cooperative.getNom());
                authResponse.setUsername(cooperative.getEmail());
                return authResponse;
            }

            Admin admin= adminRepository.findByEmail(authResponse.getUsername()).orElse(null);
            if(admin!=null)
            {
                authResponse.setRoles(roles);
                authResponse.setFirstname(admin.getPrenom());
                authResponse.setLastname(admin.getNom());
                authResponse.setUsername(admin.getEmail());
                return authResponse;
            }

        return authResponse;
        }
        else {
            System.out.println("Bad Credentials");
            throw new UsernameNotFoundException("invalid user request !");
        }



    }


    }
