package saballestra.deeping.mapstruct.domain.dto;

import lombok.Data;
import saballestra.deeping.mapstruct.domain.embedded.Telefone;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author s2it_leferreira
 * @since 1/31/18 11:38 AM
 */
@Data
public class PessoaDTO {

    private String nome;

    private Integer idade;

    private BigDecimal peso;

    private Telefone telefonePrincipal;

    private String telefoneOpcional;

    private String dataDeCriacao;

    private String dataDeAtualizacao;

    private String dataDeNascimento;

}
