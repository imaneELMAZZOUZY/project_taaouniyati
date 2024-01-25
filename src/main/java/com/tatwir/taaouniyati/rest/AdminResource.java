package com.tatwir.taaouniyati.rest;

import com.tatwir.taaouniyati.model.AdminDTO;
import com.tatwir.taaouniyati.service.AdminService;
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
@RequestMapping(value = "/api/admins", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminResource {

    private final AdminService adminService;

    public AdminResource(final AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(adminService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAdmin(@RequestBody @Valid final AdminDTO adminDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (adminService.emailExists(adminDTO.getEmail())) {
            bindingResult.rejectValue("Email", "Exists.admin.email");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final Long createdId = adminService.create(adminDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAdmin(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AdminDTO adminDTO) {
        adminService.update(id, adminDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable(name = "id") final Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
