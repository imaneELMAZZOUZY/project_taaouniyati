package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Produit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByProduits(Produit produit);


    boolean existsByEmailIgnoreCase(String email);

}
