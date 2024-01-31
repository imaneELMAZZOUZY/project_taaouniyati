package com.tatwir.taaouniyati.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String nom;

    @Column
    private String description;

    @Column
    private String prix;

    @Column(length = 100000)
    @Lob
    private byte[] photo;

    @Column
    private String poids;

    @Column
    private Boolean estValide;

    @Column
    private Boolean inStock;

    @ManyToMany(mappedBy = "produits")
    private Set<Client> clients;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cooperative cooperative;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

}
