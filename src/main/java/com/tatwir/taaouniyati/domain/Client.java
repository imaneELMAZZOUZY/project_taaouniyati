package com.tatwir.taaouniyati.domain;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String telephone;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "produits_clients",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Set<Produit> produits;

}
