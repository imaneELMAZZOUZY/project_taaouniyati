package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByCooperative(Cooperative cooperative);

}
