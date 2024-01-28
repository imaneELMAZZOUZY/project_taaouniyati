package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.CooperativeDTO;
import com.tatwir.taaouniyati.service.CooperativeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
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

    public CooperativeResource(final CooperativeService cooperativeService) {
        this.cooperativeService = cooperativeService;
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

    @PostMapping
    public ResponseEntity<Long> createCooperative(
            HttpServletRequest request,
            @ModelAttribute  final CooperativeDTO cooperativeDTO,
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
