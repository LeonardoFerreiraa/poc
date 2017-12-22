package br.com.leonardoferreira.report.controller;

import br.com.leonardoferreira.report.domain.Project;
import br.com.leonardoferreira.report.domain.form.ProjectForm;
import br.com.leonardoferreira.report.security.annotation.SecureAccess;
import br.com.leonardoferreira.report.security.domain.CurrentUser;
import br.com.leonardoferreira.report.service.ProjectService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SecureAccess
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
        private CurrentUser currentUser;

    @GetMapping
    public Page<Project> index(
        @Spec(path = "name", spec = Like.class) Specification<Project> project,
        @PageableDefault(size = PAGESIZE) Pageable pageable) {
        return projectService.index(project, pageable);
    }

    @PostMapping
    public HttpEntity<Void> create(@Valid @RequestBody ProjectForm projectForm) {
        projectService.create(projectForm);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping
    public HttpEntity<Void> update(@Valid @RequestBody ProjectForm projectForm) {
        projectService.update(projectForm);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public HttpEntity<Project> get(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Void> delete(@PathVariable Long id) {
        projectService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
