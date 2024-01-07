package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategorieDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String nom;

    @Size(max = 255)
    private String description;

}
