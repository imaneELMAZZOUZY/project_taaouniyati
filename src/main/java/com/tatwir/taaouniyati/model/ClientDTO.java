package com.tatwir.taaouniyati.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientDTO {

    private Long id;

        private String nom;

    private String prenom;

    @NotNull
    private String email;

    private String telephone;

    @NotNull
    private String password;


 //   private List<Long> produits;

}
