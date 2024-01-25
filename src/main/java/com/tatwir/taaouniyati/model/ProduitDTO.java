package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProduitDTO {


    private Long id;


    private String nom;


    private String description;


    private String prix;

    private byte[] photo;


    private String poids;

    private Boolean estValide;

    private Boolean inStock;


    private Long categorie;


    private Long cooperative;


    private Long admin;

}
