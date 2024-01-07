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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<CategorieDTO> getCategorie(@PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(categorieService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(
            @RequestBody @Valid final CategorieDTO categorieDTO, final BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && categorieDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && categorieService.idExists(categorieDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.categorie.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final String createdId = categorieService.create(categorieDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategorie(@PathVariable(name = "id") final String id,
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        categorieService.update(id, categorieDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable(name = "id") final String id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
