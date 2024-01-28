package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Produit;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByProduits(Produit produit);


    Optional<Client> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

}
