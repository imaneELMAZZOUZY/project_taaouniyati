package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Cooperative;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CooperativeRepository extends JpaRepository<Cooperative, String> {

    boolean existsByIdIgnoreCase(String id);

    boolean existsByEmailIgnoreCase(String email);

}