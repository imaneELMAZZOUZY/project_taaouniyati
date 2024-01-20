package com.tatwir.taaouniyati.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Cooperative {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "\"description\"")
    private String description;

    @Column
    private String localisation;

    @Column
    private String photo;

    @Column
    private String address;

    @Column
    private String telephone;

    @Column
    private Boolean estValide;

    @OneToMany(mappedBy = "cooperative")
    private Set<Produit> produits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

}
