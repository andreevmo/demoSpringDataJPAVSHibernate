package com.example.demoSpringVSHibernate.springDataJPAImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.EmployeeRepository;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;

    @Override
    public EmployeeDTO get(Long id) {
        return createEmployeeDTO(employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("employee with id " + id + " not found")));
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(this::createEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(createEmployee(employeeDTO));
        return createEmployeeDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        Employee employeeFromDB = employeeRepository.findById(id)
                .orElseThrow((() -> new NoSuchElementException("employee with id " + id + " not found")));
        Employee employee = createEmployee(employeeDTO);
        employee.setId(id);
        employee.setCreatedAt(employeeFromDB.getCreatedAt());
        return createEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        employeeRepository.findById(id)
                .orElseThrow((() -> new NoSuchElementException("employee with id " + id + " not found")));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Role role = roleRepository.findById(employeeDTO.getRoleId())
                .orElseThrow(() ->
                        new NoSuchElementException("role with id " + employeeDTO.getRoleId() + " not found"));
        return Employee.builder()
                .firstname(employeeDTO.getFirstname())
                .lastname(employeeDTO.getLastname())
                .role(role)
                .build();
    }
}
