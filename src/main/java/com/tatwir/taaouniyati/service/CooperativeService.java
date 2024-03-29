package com.tatwir.taaouniyati.service;

import com.tatwir.taaouniyati.domain.Admin;
import com.tatwir.taaouniyati.domain.Cooperative;
import com.tatwir.taaouniyati.model.CooperativeDTO;
import com.tatwir.taaouniyati.repos.AdminRepository;
import com.tatwir.taaouniyati.repos.CooperativeRepository;
import com.tatwir.taaouniyati.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CooperativeService {

    private final CooperativeRepository cooperativeRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public CooperativeService(final CooperativeRepository cooperativeRepository,
            final AdminRepository adminRepository,
            final PasswordEncoder passwordEncoder
    ) {
        this.cooperativeRepository = cooperativeRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CooperativeDTO> findAll() {
        final List<Cooperative> cooperatives = cooperativeRepository.findAll(Sort.by("id"));
        return cooperatives.stream()
                .map(cooperative -> mapToDTO(cooperative, new CooperativeDTO()))
                .toList();
    }

    public CooperativeDTO get(final Long id) {
        return cooperativeRepository.findById(id)
                .map(cooperative -> mapToDTO(cooperative, new CooperativeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public CooperativeDTO getByEmail(final String email) {
        return cooperativeRepository.findByEmail(email)
                .map(cooperative -> mapToDTO(cooperative, new CooperativeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CooperativeDTO cooperativeDTO) {
        final Cooperative cooperative = new Cooperative();
        cooperativeDTO.setPassword(passwordEncoder.encode(cooperativeDTO.getPassword()));
        mapToEntity(cooperativeDTO, cooperative);
        return cooperativeRepository.save(cooperative).getId();
    }

    public void update(final Long id, final CooperativeDTO cooperativeDTO) {
        final Cooperative cooperative = cooperativeRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        mapToEntity(cooperativeDTO, cooperative);
        cooperativeRepository.save(cooperative);
    }

    public void delete(final Long id) {
        cooperativeRepository.deleteById(id);
    }

    private CooperativeDTO mapToDTO(final Cooperative cooperative,
            final CooperativeDTO cooperativeDTO) {
        cooperativeDTO.setId(cooperative.getId());
        cooperativeDTO.setNom(cooperative.getNom());
        cooperativeDTO.setEmail(cooperative.getEmail());
        cooperativeDTO.setPassword(cooperative.getPassword());
        cooperativeDTO.setDescription(cooperative.getDescription());
        cooperativeDTO.setLocalisation(cooperative.getLocalisation());
        cooperativeDTO.setPhoto(cooperative.getPhoto());
        cooperativeDTO.setAddress(cooperative.getAddress());
        cooperativeDTO.setTelephone(cooperative.getTelephone());
        cooperativeDTO.setEstValide(cooperative.getEstValide());
        cooperativeDTO.setAdmin(cooperative.getAdmin() == null ? null : cooperative.getAdmin().getId());
        return cooperativeDTO;
    }

    private Cooperative mapToEntity(final CooperativeDTO cooperativeDTO,
            final Cooperative cooperative) {
        cooperative.setNom(cooperativeDTO.getNom());
        cooperative.setEmail(cooperativeDTO.getEmail());
        cooperative.setPassword(cooperativeDTO.getPassword());
        cooperative.setDescription(cooperativeDTO.getDescription());
        cooperative.setLocalisation(cooperativeDTO.getLocalisation());
        cooperative.setPhoto(cooperativeDTO.getPhoto());
        cooperative.setAddress(cooperativeDTO.getAddress());
        cooperative.setTelephone(cooperativeDTO.getTelephone());
        cooperative.setEstValide(cooperativeDTO.getEstValide());
        final Admin admin = cooperativeDTO.getAdmin() == null ? null : adminRepository.findById(cooperativeDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        cooperative.setAdmin(admin);
        return cooperative;
    }


    public boolean emailExists(final String email) {
        return cooperativeRepository.existsByEmailIgnoreCase(email);
    }

}
