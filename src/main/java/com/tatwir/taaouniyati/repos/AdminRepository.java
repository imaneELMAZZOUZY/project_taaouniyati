package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {


    boolean existsByEmailIgnoreCase(String email);

}
