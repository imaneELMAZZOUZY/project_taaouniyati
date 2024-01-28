package com.tatwir.taaouniyati.config;



import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Cooperative;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserInfoUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities= new ArrayList<>();

    public UserInfoUserDetails(Object userInfo) {
        if (userInfo instanceof Client) {
            Client client = (Client) userInfo;
            this.username = client.getEmail();
            this.password = client.getPassword();
            authorities.add(new SimpleGrantedAuthority("ROLE_Client"));
        } else if (userInfo instanceof Cooperative) {
            Cooperative cooperative = (Cooperative) userInfo;
            this.username = cooperative.getEmail();
            this.password = cooperative.getPassword();
            authorities.add(new SimpleGrantedAuthority("ROLE_Cooperative"));
        }
        else if (userInfo instanceof Admin) {
            Admin admin = (Admin) userInfo;
            this.username = admin.getEmail();
            this.password = admin.getPassword();
            authorities.add(new SimpleGrantedAuthority("ROLE_Admin"));
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
