package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String nom;

    @Size(max = 255)
    private String prenom;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

}
