package com.tatwir.taaouniyati.repos;

import com.tatwir.taaouniyati.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {


    boolean existsByNomIgnoreCase(String nom);

}
