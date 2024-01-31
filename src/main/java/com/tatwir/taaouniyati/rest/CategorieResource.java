package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.CategorieDTO;
import com.tatwir.taaouniyati.service.CategorieService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategorieResource {

    private final CategorieService categorieService;

    public CategorieResource(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorie(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(categorieService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCategorie(
            @RequestBody @Valid final CategorieDTO categorieDTO, final BindingResult bindingResult)
            throws MethodArgumentNotValidException {

        if ( categorieService.categorieExists(categorieDTO.getNom())) {
            bindingResult.rejectValue("categorie", "Exists.categorie");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final Long createdId = categorieService.create(categorieDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCategorie(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        categorieService.update(id, categorieDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable(name = "id") final Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
