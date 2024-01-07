package com.tatwir.taaouniyati.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Client {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

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
            name = "ProduitsClients",
            joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "produitId")
    )
    private Set<Produit> produits;

}
