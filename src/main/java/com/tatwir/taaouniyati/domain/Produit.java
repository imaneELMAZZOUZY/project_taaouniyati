package com.tatwir.taaouniyati.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Produit {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String nom;

    @Column(name = "\"description\"")
    private String description;

    @Column
    private String prix;

    @Column
    private String photo;

    @Column
    private String poids;

    @Column
    private Boolean estValide;

    @Column
    private Boolean inStock;

    @ManyToMany(mappedBy = "produits")
    private Set<Client> clients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id")
    private Cooperative cooperative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

}
