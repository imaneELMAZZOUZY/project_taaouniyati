package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CooperativeDTO {

    private Long id;

    private String nom;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String description;

    private String localisation;


    private byte[] photo;

    private String address;

    private String telephone;

    private Boolean estValide;

    private Long admin;

}
