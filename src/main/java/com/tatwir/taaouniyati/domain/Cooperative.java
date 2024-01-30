package com.tatwir.taaouniyati.domain;

import jakarta.persistence.*;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cooperative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String description;

    @Column
    private String localisation;

    @Column(length = 100000)
    @Lob
    private byte[] photo;

    @Column
    private String address;

    @Column
    private String telephone;

    @Column
    private Boolean estValide;

    @OneToMany(mappedBy = "cooperative")
    @JsonIgnore
    private Set<Produit> produits;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

}
