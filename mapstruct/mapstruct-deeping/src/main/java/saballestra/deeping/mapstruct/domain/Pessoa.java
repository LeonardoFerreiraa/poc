package saballestra.deeping.mapstruct.domain;

import lombok.Data;
import saballestra.deeping.mapstruct.domain.embedded.Telefone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author s2it_leferreira
 * @since 1/31/18 11:38 AM
 */
@Data
public class Pessoa {

    private String primeiroNome;

    private String sobreNome;

    private Long idade;

    private Double peso;

    private Telefone telefone1;

    private Telefone telefone2;

    private Date dataDeCriacao;

    private Calendar dataDeAtualizacao;

    private LocalDate dataDeNascimento;

}
