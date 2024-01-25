package com.tatwir.taaouniyati.repos;


import com.tatwir.taaouniyati.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Page<Produit> findByCategorieAndCooperative(String categorie, String cooperative, Pageable pageable);

}
