package com.tatwir.taaouniyati.domain;

import jakarta.persistence.*;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String description;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    private Set<Produit> produits;

}
