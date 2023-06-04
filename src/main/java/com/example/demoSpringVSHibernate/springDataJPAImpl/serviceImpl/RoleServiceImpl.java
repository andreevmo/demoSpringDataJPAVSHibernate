package com.example.demoSpringVSHibernate.springDataJPAImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.RoleService;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public RoleDTO get(Long id) {
        return createRoleDTO(getRoleById(id));
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("role with id " + id + " not found"));
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream().map(this::createRoleDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDTO save(RoleDTO dto) {
        return createRoleDTO(roleRepository.save(createRole(dto)));
    }

    @Override
    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        Role roleFromDB = getRoleById(id);
        roleFromDB.setName(dto.getName());
        return createRoleDTO(roleRepository.save(roleFromDB));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getRoleById(id);
        roleRepository.deleteById(id);
    }
}
