package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long> {


    boolean existsByEmailIgnoreCase(String email);

    Optional<Admin> findByEmail(String email);
}
