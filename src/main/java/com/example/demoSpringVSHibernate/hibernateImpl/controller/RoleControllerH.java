package com.example.demoSpringVSHibernate.hibernateImpl.controller;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl.RoleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.RoleController.ROLES_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-hibernate}" + ROLES_CONTROLLER_PATH)
public class RoleControllerH {

    public static final String ROLES_CONTROLLER_PATH = "/roles";
    public static final String ID = "/{id}";
    private final RoleServiceImpl service = new RoleServiceImpl();

    @GetMapping(path = ID)
    public RoleDTO getRole(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<RoleDTO> getRoles() {
        return service.getAll();
    }

    @PostMapping
    public RoleDTO createRole(@RequestBody RoleDTO role) {
        return service.save(role);
    }

    @PutMapping(path = ID)
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
        return service.update(id, role);
    }

    @DeleteMapping(path = ID)
    public void deleteRole(@PathVariable Long id) {
        service.delete(id);
    }
}
