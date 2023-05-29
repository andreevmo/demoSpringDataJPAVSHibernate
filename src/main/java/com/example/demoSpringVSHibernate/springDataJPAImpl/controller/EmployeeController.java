package com.example.demoSpringVSHibernate.springDataJPAImpl.controller;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.EmployeeController.EMPLOYEE_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-spring}" + EMPLOYEE_CONTROLLER_PATH)
@AllArgsConstructor
public class EmployeeController {

    public static final String EMPLOYEE_CONTROLLER_PATH = "/employees";
    public static final String ID = "/{id}";
    private EmployeeService service;

    @GetMapping(path = ID)
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<EmployeeDTO> getEmployees() {
        return service.getAll();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return service.save(employee);
    }

    @PutMapping(path = ID)
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        return service.update(id, employee);
    }

    @DeleteMapping(path = ID)
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }
}
