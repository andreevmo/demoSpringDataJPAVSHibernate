package com.example.demoSpringVSHibernate.hibernateImpl.controller;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.EmployeeController.EMPLOYEE_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-hibernate}" + EMPLOYEE_CONTROLLER_PATH)
public class EmployeeControllerH {

    public static final String EMPLOYEE_CONTROLLER_PATH = "/employees";
    public static final String ID = "/{id}";
    private final EmployeeServiceImpl service = new EmployeeServiceImpl();

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
