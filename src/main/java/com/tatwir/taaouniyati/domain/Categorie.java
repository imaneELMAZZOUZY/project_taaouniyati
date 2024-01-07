package com.tatwir.taaouniyati.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Categorie {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String nom;

    @Column(name = "\"description\"")
    private String description;

    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits;

}
