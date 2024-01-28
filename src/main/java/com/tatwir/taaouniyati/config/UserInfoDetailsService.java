package com.tatwir.taaouniyati.config;



import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.repos.AdminRepository;
import com.tatwir.taaouniyati.repos.ClientRepository;
import com.tatwir.taaouniyati.repos.CooperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserInfoDetailsService implements UserDetailsService {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username).orElse(null);

        if (client != null) {
            return new UserInfoUserDetails(client);
        }

        Cooperative cooperative = cooperativeRepository.findByEmail(username).orElse(null);

        if (cooperative != null) {
            return new UserInfoUserDetails(cooperative);
        }

        Admin admin = adminRepository.findByEmail(username).orElse(null);

        if (admin != null) {
            return new UserInfoUserDetails(admin);
        }

        throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
    }



    }





