package br.com.leonardoferreira.report.service;

import br.com.leonardoferreira.report.domain.Project;
import br.com.leonardoferreira.report.domain.form.ProjectForm;
import br.com.leonardoferreira.report.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Page<Project> index(Specification<Project> project, Pageable pageable) {
        log.info("Method=index, Param={}", pageable);
        return projectRepository.findAll(project, pageable);
    }

    public void create(ProjectForm projectForm) {
        log.info("Method=create, Param={}", projectForm);
        Project project = new Project();
        BeanUtils.copyProperties(projectForm, project);

        projectRepository.save(project);
    }

    public void update(ProjectForm projectForm) {
        log.info("Method=update, Param={}", projectForm);
        Project project = projectRepository.findOne(projectForm.getId());
        BeanUtils.copyProperties(projectForm, project);
        projectRepository.save(project);
    }

    public Project findOne(Long id) {
        log.info("Method=findOne, Param={}", id);
        return projectRepository.findOne(id);
    }

    public void remove(Long id) {
        log.info("Method=remove, Param={}", id);
        projectRepository.delete(id);
    }
}
