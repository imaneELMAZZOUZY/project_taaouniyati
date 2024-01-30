package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.domain.Client;
import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.domain.Produit;
import com.tatwir.taaouniyati.model.ClientDTO;
import com.tatwir.taaouniyati.model.CooperativeDTO;
import com.tatwir.taaouniyati.model.ProduitDTO;
import com.tatwir.taaouniyati.service.ClientService;
import com.tatwir.taaouniyati.service.CooperativeService;
import com.tatwir.taaouniyati.service.ProduitService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.ToString;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
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
@RequestMapping(value = "/api/cooperatives", produces = MediaType.APPLICATION_JSON_VALUE)
public class CooperativeResource {

    private final CooperativeService cooperativeService;
    private final ClientService clientService;
    private final ProduitService produitService;

    public CooperativeResource(final CooperativeService cooperativeService,
            final ClientService clientService,
            ProduitService produitService) {
        this.cooperativeService = cooperativeService;
        this.clientService = clientService;
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<CooperativeDTO>> getAllCooperatives() {
        return ResponseEntity.ok(cooperativeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CooperativeDTO> getCooperative(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cooperativeService.get(id));
    }

    @GetMapping("/{id}/clients-interested")
    public ResponseEntity<List<ClientDTO>> getClientsInterested(@PathVariable(name = "id") final Long id) {
        CooperativeDTO cooperativeDTO = cooperativeService.get(id);
        System.out.println("==========================" + id);

        if (cooperativeDTO != null) {
            // Utilisez la méthode getAllProduitsWithFilter avec l'ID de la coopérative
            List<ProduitDTO> produitsDTO = produitService.findByCooperative(cooperativeDTO.getId());

            // Affiche chaque produit dans la console
            for (ProduitDTO produitDTO : produitsDTO) {
                System.out.println("-----------------------------");
                System.out.println("Produit ID: " + produitDTO.getId());
                System.out.println("Produit Nom: " + produitDTO.getNom());
                // Ajoutez d'autres propriétés que vous souhaitez afficher
                System.out.println("-----------------------------");
            }

            // Extrayez les clients intéressés des produits
            Set<Client> clientsInterested = produitsDTO.stream()
                    .filter(produitDTO -> produitDTO.getClients() != null)
                    .flatMap(produitDTO -> produitDTO.getClients().stream())
                    .collect(Collectors.toSet());

            for (Client client : clientsInterested) {
                System.out.println("-----------------------------");
                System.out.println("Client ID: " + client.getId());
                System.out.println("Client Nom: " + client.getNom());
                // Ajoutez d'autres propriétés que vous souhaitez afficher
                System.out.println("-----------------------------");
            }

            List<ClientDTO> clientDTOs = clientsInterested.stream()
                    .map(client -> clientService.mapToDTO(client, new ClientDTO()))
                    .toList();

            return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/connected-id-by-email")
    public ResponseEntity<String> getConnectedCooperativeIdByEmail(@RequestParam("email") String cooperativeEmail) {
        Cooperative cooperative = cooperativeService.getCooperativeIdByEmail(cooperativeEmail);

        if (cooperative != null) {
            System.out.println("-----------------------------");
            System.out.println("Cooperative ID: " + cooperative.getId());
            // Ajoutez d'autres propriétés que vous souhaitez afficher
            System.out.println("-----------------------------");
            return ResponseEntity.ok(cooperative.getId().toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/{id}/produits")
    // public ResponseEntity<List<ProduitDTO>>
    // getProduitsByCooperativeId(@PathVariable(name = "id") final Long id) {
    // CooperativeDTO cooperativeDTO = cooperativeService.get(id);

    // if (cooperativeDTO != null) {
    // // Utilisez la méthode findByCooperative avec l'ID de la coopérative
    // List<ProduitDTO> produitsDTO =
    // produitService.findByCooperative(cooperativeDTO.getId());

    // // Affichez chaque produit dans la console (vous pouvez retirer cela dans la
    // // version finale)
    // for (ProduitDTO produitDTO : produitsDTO) {
    // System.out.println("-----------------------------");
    // System.out.println("Produit ID: " + produitDTO.getId());
    // System.out.println("Produit Nom: " + produitDTO.getNom());
    // // Ajoutez d'autres propriétés que vous souhaitez afficher
    // System.out.println("-----------------------------");
    // }

    // return ResponseEntity.ok(produitsDTO);
    // }

    // // Retournez une réponse appropriée si la coopérative n'est pas trouvée
    // return ResponseEntity.notFound().build();
    // }

    @GetMapping("/{id}/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsByCooperativeId(@PathVariable(name = "id") final Long id) {
        List<ProduitDTO> produitsDTO = cooperativeService.getProduitsByCooperativeId(id);

        if (!produitsDTO.isEmpty()) {
            return ResponseEntity.ok(produitsDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Long> createCooperative(
            HttpServletRequest request,
            @ModelAttribute final CooperativeDTO cooperativeDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (cooperativeService.emailExists(cooperativeDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.cooperative.email");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        MultipartFile photo = ((MultipartHttpServletRequest) request).getFile("cooperativephoto");
        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] logoBytes = photo.getBytes();
                cooperativeDTO.setPhoto(logoBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error while processing photo upload");
            }
        }
        final Long createdId = cooperativeService.create(cooperativeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCooperative(
            HttpServletRequest request,
            @PathVariable(name = "id") final Long id,
            @ModelAttribute final CooperativeDTO cooperativeDTO) {

        MultipartFile photo = ((MultipartHttpServletRequest) request).getFile("cooperativephoto");

        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] logoBytes = photo.getBytes();
                cooperativeDTO.setPhoto(logoBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error while processing logo upload");
            }
        }
        cooperativeService.update(id, cooperativeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCooperative(@PathVariable(name = "id") final Long id) {
        cooperativeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
