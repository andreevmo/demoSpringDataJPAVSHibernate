package com.example.demoSpringVSHibernate.springDataJPAImpl.controller;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.RoleController.ROLES_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-spring}" + ROLES_CONTROLLER_PATH)
@AllArgsConstructor
@Tag(name = "Role with SpringDataJPA")
public class RoleController {

    public static final String ROLES_CONTROLLER_PATH = "/roles";
    public static final String ID = "/{id}";
    private RoleService service;

    @GetMapping(path = ID)
    public RoleDTO getRole(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<RoleDTO> getRoles() {
        return service.getAll();
    }

    @PostMapping
    public RoleDTO createRole(@RequestBody @Valid RoleDTO role) {
        return service.save(role);
    }

    @PutMapping(path = ID)
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody @Valid RoleDTO role) {
        return service.update(id, role);
    }

    @DeleteMapping(path = ID)
    public void deleteRole(@PathVariable Long id) {
        service.delete(id);
    }
}
