package br.com.leonardoferreira.report.repository;

import br.com.leonardoferreira.report.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Page<Project> findAll(Specification<Project> project, Pageable pageable);
}
