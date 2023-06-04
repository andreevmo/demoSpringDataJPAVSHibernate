package com.example.demoSpringVSHibernate.springDataJPAImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.EmployeeRepository;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleServiceImpl roleService;
    private ProjectRepository projectRepository;

    @Override
    public EmployeeDTO get(Long id) {
        return createEmployeeDTO(getEmployeeById(id));
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("employee with id " + id + " not found"));
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(this::createEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO dto) {
        return createEmployeeDTO(employeeRepository.save(createEmployee(dto)));
    }

    @Override
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee employee = createEmployee(dto);
        employee.setId(id);
        employee.setCreatedAt(getEmployeeById(id).getCreatedAt());
        return createEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getEmployeeById(id);
        employeeRepository.deleteById(id);
    }

    private Employee createEmployee(EmployeeDTO employeeDTO) {
        Role role = roleService.getRoleById(employeeDTO.getRoleId());
        Set<Project> projects = null;
        if (employeeDTO.getProjectIds() != null) {
            projects = employeeDTO.getProjectIds().stream()
                    .map(id -> projectRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("project with id " + id + " not found")))
                    .collect(Collectors.toSet());
        }
        return Employee.builder()
                .firstname(employeeDTO.getFirstname())
                .lastname(employeeDTO.getLastname())
                .role(role)
                .projects(projects)
                .build();
    }
}
