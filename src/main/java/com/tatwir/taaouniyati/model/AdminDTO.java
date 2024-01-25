package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminDTO {

    private Long id;

    private String nom;

    private String prenom;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
