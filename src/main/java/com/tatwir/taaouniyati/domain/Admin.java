package com.tatwir.taaouniyati.domain;

import jakarta.persistence.*;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private Set<Cooperative> cooperativesValides;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private Set<Produit> produitsValides;

}
