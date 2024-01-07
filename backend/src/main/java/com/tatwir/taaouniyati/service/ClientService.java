package com.tatwir.taaouniyati.service;

import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Produit;
import com.tatwir.taaouniyati.model.ClientDTO;
import com.tatwir.taaouniyati.repos.ClientRepository;
import com.tatwir.taaouniyati.repos.ProduitRepository;
import com.tatwir.taaouniyati.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    public ClientService(final ClientRepository clientRepository,
            final ProduitRepository produitRepository) {
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
    }

    public List<ClientDTO> findAll() {
        final List<Client> clients = clientRepository.findAll(Sort.by("id"));
        return clients.stream()
                .map(client -> mapToDTO(client, new ClientDTO()))
                .toList();
    }

    public ClientDTO get(final String id) {
        return clientRepository.findById(id)
                .map(client -> mapToDTO(client, new ClientDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ClientDTO clientDTO) {
        final Client client = new Client();
        mapToEntity(clientDTO, client);
        client.setId(clientDTO.getId());
        return clientRepository.save(client).getId();
    }

    public void update(final String id, final ClientDTO clientDTO) {
        final Client client = clientRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clientDTO, client);
        clientRepository.save(client);
    }

    public void delete(final String id) {
        clientRepository.deleteById(id);
    }

    private ClientDTO mapToDTO(final Client client, final ClientDTO clientDTO) {
        clientDTO.setId(client.getId());
        clientDTO.setNom(client.getNom());
        clientDTO.setPrenom(client.getPrenom());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setTelephone(client.getTelephone());
        clientDTO.setPassword(client.getPassword());
        clientDTO.setProduits(client.getProduits().stream()
                .map(produit -> produit.getId())
                .toList());
        return clientDTO;
    }

    private Client mapToEntity(final ClientDTO clientDTO, final Client client) {
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setEmail(clientDTO.getEmail());
        client.setTelephone(clientDTO.getTelephone());
        client.setPassword(clientDTO.getPassword());
        final List<Produit> produits = produitRepository.findAllById(
                clientDTO.getProduits() == null ? Collections.emptyList() : clientDTO.getProduits());
        if (produits.size() != (clientDTO.getProduits() == null ? 0 : clientDTO.getProduits().size())) {
            throw new NotFoundException("one of produits not found");
        }
        client.setProduits(produits.stream().collect(Collectors.toSet()));
        return client;
    }

    public boolean idExists(final String id) {
        return clientRepository.existsByIdIgnoreCase(id);
    }

    public boolean emailExists(final String email) {
        return clientRepository.existsByEmailIgnoreCase(email);
    }

}
