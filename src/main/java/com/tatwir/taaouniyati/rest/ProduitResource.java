package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.ProduitDTO;
import com.tatwir.taaouniyati.service.ProduitService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/produits", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProduitResource {

    private final ProduitService produitService;

    public ProduitResource(final ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        return ResponseEntity.ok(produitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(produitService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createProduit(@RequestBody @Valid final ProduitDTO produitDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && produitDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && produitService.idExists(produitDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.produit.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final String createdId = produitService.create(produitDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduit(@PathVariable(name = "id") final String id,
            @RequestBody @Valid final ProduitDTO produitDTO) {
        produitService.update(id, produitDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable(name = "id") final String id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
