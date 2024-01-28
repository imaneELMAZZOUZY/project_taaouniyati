package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.ClientDTO;
import com.tatwir.taaouniyati.service.ClientService;
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
@RequestMapping(value = "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientResource {

    private final ClientService clientService;

    public ClientResource(final ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(clientService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createClient(@RequestBody @Valid final ClientDTO clientDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (clientService.emailExists(clientDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.client.email");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final Long createdId = clientService.create(clientDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateClient(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ClientDTO clientDTO) {
        clientService.update(id, clientDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable(name = "id") final Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
