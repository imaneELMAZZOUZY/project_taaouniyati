package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProduitDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String nom;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String prix;

    @Size(max = 255)
    private String photo;

    @Size(max = 255)
    private String poids;

    private Boolean estValide;

    private Boolean inStock;

    @Size(max = 255)
    private String categorie;

    @Size(max = 255)
    private String cooperative;

    @Size(max = 255)
    private String admin;

}
