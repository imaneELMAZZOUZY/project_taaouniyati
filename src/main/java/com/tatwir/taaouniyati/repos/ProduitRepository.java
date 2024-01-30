package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByCooperativeId(Long cooperativeId);

    List<Produit> findByEstValideFalse();

}
