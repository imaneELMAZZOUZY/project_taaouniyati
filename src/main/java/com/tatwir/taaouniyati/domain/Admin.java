package com.tatwir.taaouniyati.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"Admin\"")
@Getter
@Setter
public class Admin {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "admin")
    private Set<Cooperative> cooperativesValides;

    @OneToMany(mappedBy = "admin")
    private Set<Produit> produitsValides;

}
