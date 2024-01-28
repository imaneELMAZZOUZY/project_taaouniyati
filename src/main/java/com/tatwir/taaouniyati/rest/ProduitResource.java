package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.ProduitDTO;
import com.tatwir.taaouniyati.service.ProduitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/produits", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProduitResource {

    private final ProduitService produitService;

    public ProduitResource(final ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getProduitsWithFilter(
            @RequestParam(required = false) Long cooperativeId,
            @RequestParam(required = false) Long categorieId){
        List<ProduitDTO> produits = produitService.getAllProduitsWithFilter(cooperativeId, categorieId);
        return ResponseEntity.ok(produits);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(produitService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createProduit(HttpServletRequest request,
                                              @ModelAttribute final ProduitDTO produitDTO,
                                              final BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }

        MultipartFile photo = ((MultipartHttpServletRequest) request).getFile("productphoto");
        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] logoBytes = photo.getBytes();
                produitDTO.setPhoto(logoBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error while processing photo upload");
            }
        }
        final Long createdId = produitService.create(produitDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduit(HttpServletRequest request,
                                             @PathVariable(name = "id") final Long id,
                                              @ModelAttribute final ProduitDTO produitDTO) {

        MultipartFile photo = ((MultipartHttpServletRequest) request).getFile("productphoto");
        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] logoBytes = photo.getBytes();
                produitDTO.setPhoto(logoBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error while processing photo upload");
            }
        }
        produitService.update(id, produitDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable(name = "id") final Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/interest")

public ResponseEntity<Boolean> markProductAsInteresting(@RequestParam("productId") Long productId,
                                                     @RequestParam("clientEmail") String clientEmail)
    {
        return ResponseEntity.ok(produitService.markProductAsInteresting(productId,clientEmail));
    }





}
