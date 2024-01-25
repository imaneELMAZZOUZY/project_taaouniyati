package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Cooperative;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CooperativeRepository extends JpaRepository<Cooperative, Long> {


    boolean existsByEmailIgnoreCase(String email);

}
