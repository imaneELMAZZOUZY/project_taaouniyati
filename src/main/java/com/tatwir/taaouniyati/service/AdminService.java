package com.tatwir.taaouniyati.service;

import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.model.AdminDTO;
import com.tatwir.taaouniyati.repos.AdminRepository;
import com.tatwir.taaouniyati.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(final AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AdminDTO> findAll() {
        final List<Admin> admins = adminRepository.findAll(Sort.by("id"));
        return admins.stream()
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .toList();
    }

    public AdminDTO get(final Long id) {
        return adminRepository.findById(id)
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdminDTO adminDTO) {
        final Admin admin = new Admin();
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        mapToEntity(adminDTO, admin);
        return adminRepository.save(admin).getId();
    }

    public void update(final Long id, final AdminDTO adminDTO) {
        final Admin admin = adminRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(adminDTO, admin);
        adminRepository.save(admin);
    }

    public void delete(final Long id) {
        adminRepository.deleteById(id);
    }

    private AdminDTO mapToDTO(final Admin admin, final AdminDTO adminDTO) {
        adminDTO.setId(admin.getId());
        adminDTO.setNom(admin.getNom());
        adminDTO.setPrenom(admin.getPrenom());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setPassword(admin.getPassword());
        return adminDTO;
    }

    private Admin mapToEntity(final AdminDTO adminDTO, final Admin admin) {
        admin.setNom(adminDTO.getNom());
        admin.setPrenom(adminDTO.getPrenom());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        return admin;
    }

    public boolean EmailExists(final String email) {
        return adminRepository.existsByEmailIgnoreCase(email);
    }

    public boolean emailExists(final String email) {
        return adminRepository.existsByEmailIgnoreCase(email);
    }

}
