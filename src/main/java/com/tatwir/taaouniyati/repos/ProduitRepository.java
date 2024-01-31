package com.tatwir.taaouniyati.repos;


import com.tatwir.taaouniyati.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByEstValideFalse();

    List<Produit> findByCategorieIdAndCooperativeId(Long categorieId, Long cooperativeId);
    List<Produit> findByCooperativeId(Long cooperativeId);
    void deleteByCooperativeId(Long cooperativeId);


}
