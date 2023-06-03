package com.example.demoSpringVSHibernate.hibernateImpl.controller;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl.ProjectServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.ProjectController.PROJECT_CONTROLLER_PATH;

@RestController
@RequestMapping(path = "${base-url-hibernate}" + PROJECT_CONTROLLER_PATH)
@Tag(name = "Project with Hibernate")
public class ProjectControllerH {

    public static final String PROJECT_CONTROLLER_PATH = "/projects";
    public static final String ID = "/{id}";
    private final ProjectServiceImpl service = new ProjectServiceImpl();

    @GetMapping(path = ID)
    public ProjectDTO getProject(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<ProjectDTO> getProjects() {
        return service.getAll();
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody @Valid ProjectDTO project) {
        return service.save(project);
    }

    @PutMapping(path = ID)
    public ProjectDTO updateProject(@PathVariable @Valid Long id, @RequestBody ProjectDTO project) {
        return service.update(id, project);
    }

    @DeleteMapping(path = ID)
    public void deleteProject(@PathVariable Long id) {
        service.delete(id);
    }
}
