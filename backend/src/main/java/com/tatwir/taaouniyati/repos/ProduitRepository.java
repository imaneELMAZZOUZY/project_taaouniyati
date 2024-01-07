package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduitRepository extends JpaRepository<Produit, String> {

    boolean existsByIdIgnoreCase(String id);

}
