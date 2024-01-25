package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategorieDTO {

    private Long id;

    private String nom;

    private String description;

}
