package com.tatwir.taaouniyati.service;

import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.domain.Categorie;
import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.domain.Produit;
import com.tatwir.taaouniyati.model.ProduitDTO;
import com.tatwir.taaouniyati.repos.AdminRepository;
import com.tatwir.taaouniyati.repos.CategorieRepository;
import com.tatwir.taaouniyati.repos.ClientRepository;
import com.tatwir.taaouniyati.repos.CooperativeRepository;
import com.tatwir.taaouniyati.repos.ProduitRepository;
import com.tatwir.taaouniyati.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final CooperativeRepository cooperativeRepository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    public ProduitService(final ProduitRepository produitRepository,
            final CategorieRepository categorieRepository,
            final CooperativeRepository cooperativeRepository,
            final AdminRepository adminRepository, final ClientRepository clientRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
        this.cooperativeRepository = cooperativeRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    public List<ProduitDTO> findAll() {
        final List<Produit> produits = produitRepository.findAll(Sort.by("id"));
        return produits.stream()
                .map(produit -> mapToDTO(produit, new ProduitDTO()))
                .toList();
    }

    public ProduitDTO get(final String id) {
        return produitRepository.findById(id)
                .map(produit -> mapToDTO(produit, new ProduitDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ProduitDTO produitDTO) {
        final Produit produit = new Produit();
        mapToEntity(produitDTO, produit);
        produit.setId(produitDTO.getId());
        return produitRepository.save(produit).getId();
    }

    public void update(final String id, final ProduitDTO produitDTO) {
        final Produit produit = produitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(produitDTO, produit);
        produitRepository.save(produit);
    }

    public void delete(final String id) {
        final Produit produit = produitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        clientRepository.findAllByProduits(produit)
                .forEach(client -> client.getProduits().remove(produit));
        produitRepository.delete(produit);
    }

    private ProduitDTO mapToDTO(final Produit produit, final ProduitDTO produitDTO) {
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());
        produitDTO.setDescription(produit.getDescription());
        produitDTO.setPrix(produit.getPrix());
        produitDTO.setPhoto(produit.getPhoto());
        produitDTO.setPoids(produit.getPoids());
        produitDTO.setEstValide(produit.getEstValide());
        produitDTO.setCategorie(produit.getCategorie() == null ? null : produit.getCategorie().getId());
        produitDTO.setCooperative(produit.getCooperative() == null ? null : produit.getCooperative().getId());
        produitDTO.setAdmin(produit.getAdmin() == null ? null : produit.getAdmin().getId());
        return produitDTO;
    }

    private Produit mapToEntity(final ProduitDTO produitDTO, final Produit produit) {
        produit.setNom(produitDTO.getNom());
        produit.setDescription(produitDTO.getDescription());
        produit.setPrix(produitDTO.getPrix());
        produit.setPhoto(produitDTO.getPhoto());
        produit.setPoids(produitDTO.getPoids());
        produit.setEstValide(produitDTO.getEstValide());
        final Categorie categorie = produitDTO.getCategorie() == null ? null : categorieRepository.findById(produitDTO.getCategorie())
                .orElseThrow(() -> new NotFoundException("categorie not found"));
        produit.setCategorie(categorie);
        final Cooperative cooperative = produitDTO.getCooperative() == null ? null : cooperativeRepository.findById(produitDTO.getCooperative())
                .orElseThrow(() -> new NotFoundException("cooperative not found"));
        produit.setCooperative(cooperative);
        final Admin admin = produitDTO.getAdmin() == null ? null : adminRepository.findById(produitDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        produit.setAdmin(admin);
        return produit;
    }

    public boolean idExists(final String id) {
        return produitRepository.existsByIdIgnoreCase(id);
    }

}