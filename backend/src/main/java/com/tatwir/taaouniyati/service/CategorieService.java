package com.tatwir.taaouniyati.service;

import com.tatwir.taaouniyati.domain.Categorie;
import com.tatwir.taaouniyati.model.CategorieDTO;
import com.tatwir.taaouniyati.repos.CategorieRepository;
import com.tatwir.taaouniyati.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(final CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<CategorieDTO> findAll() {
        final List<Categorie> categories = categorieRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .toList();
    }

    public CategorieDTO get(final String id) {
        return categorieRepository.findById(id)
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final CategorieDTO categorieDTO) {
        final Categorie categorie = new Categorie();
        mapToEntity(categorieDTO, categorie);
        categorie.setId(categorieDTO.getId());
        return categorieRepository.save(categorie).getId();
    }

    public void update(final String id, final CategorieDTO categorieDTO) {
        final Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categorieDTO, categorie);
        categorieRepository.save(categorie);
    }

    public void delete(final String id) {
        categorieRepository.deleteById(id);
    }

    private CategorieDTO mapToDTO(final Categorie categorie, final CategorieDTO categorieDTO) {
        categorieDTO.setId(categorie.getId());
        categorieDTO.setNom(categorie.getNom());
        categorieDTO.setDescription(categorie.getDescription());
        return categorieDTO;
    }

    private Categorie mapToEntity(final CategorieDTO categorieDTO, final Categorie categorie) {
        categorie.setNom(categorieDTO.getNom());
        categorie.setDescription(categorieDTO.getDescription());
        return categorie;
    }

    public boolean idExists(final String id) {
        return categorieRepository.existsByIdIgnoreCase(id);
    }

}
