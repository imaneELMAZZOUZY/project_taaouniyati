package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CooperativeDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String nom;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String localisation;

    @Size(max = 255)
    private String photo;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String telephone;

    private Boolean estValide;

    @Size(max = 255)
    private String admin;

}
