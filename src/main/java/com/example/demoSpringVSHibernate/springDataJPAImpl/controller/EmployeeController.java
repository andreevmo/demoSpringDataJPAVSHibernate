package com.example.demoSpringVSHibernate.springDataJPAImpl.controller;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.springDataJPAImpl.serviceImpl.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.EmployeeController.EMPLOYEE_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-spring}" + EMPLOYEE_CONTROLLER_PATH)
@AllArgsConstructor
@Tag(name = "Employee with SpringDataJPA")
public class EmployeeController {

    public static final String EMPLOYEE_CONTROLLER_PATH = "/employees";
    public static final String ID = "/{id}";
    private EmployeeServiceImpl service;

    @GetMapping(path = ID)
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<EmployeeDTO> getEmployees() {
        return service.getAll();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employee) {
        return service.save(employee);
    }

    @PutMapping(path = ID)
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employee) {
        return service.update(id, employee);
    }

    @DeleteMapping(path = ID)
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }
}
