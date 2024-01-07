package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.CooperativeDTO;
import com.tatwir.taaouniyati.service.CooperativeService;
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
@RequestMapping(value = "/api/cooperatives", produces = MediaType.APPLICATION_JSON_VALUE)
public class CooperativeResource {

    private final CooperativeService cooperativeService;

    public CooperativeResource(final CooperativeService cooperativeService) {
        this.cooperativeService = cooperativeService;
    }

    @GetMapping
    public ResponseEntity<List<CooperativeDTO>> getAllCooperatives() {
        return ResponseEntity.ok(cooperativeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CooperativeDTO> getCooperative(
            @PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(cooperativeService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createCooperative(
            @RequestBody @Valid final CooperativeDTO cooperativeDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && cooperativeDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && cooperativeService.idExists(cooperativeDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.cooperative.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final String createdId = cooperativeService.create(cooperativeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCooperative(@PathVariable(name = "id") final String id,
            @RequestBody @Valid final CooperativeDTO cooperativeDTO) {
        cooperativeService.update(id, cooperativeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCooperative(@PathVariable(name = "id") final String id) {
        cooperativeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
