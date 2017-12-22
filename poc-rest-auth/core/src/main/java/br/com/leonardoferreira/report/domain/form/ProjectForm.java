package br.com.leonardoferreira.report.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ProjectForm {
    private Long id;

    @NotEmpty(message = "Nome deve ser preenchido")
    private String name;
}
