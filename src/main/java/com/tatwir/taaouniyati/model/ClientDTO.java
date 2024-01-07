package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String nom;

    @Size(max = 255)
    private String prenom;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String telephone;

    @NotNull
    @Size(max = 255)
    private String password;

    private List<String> produits;

}
