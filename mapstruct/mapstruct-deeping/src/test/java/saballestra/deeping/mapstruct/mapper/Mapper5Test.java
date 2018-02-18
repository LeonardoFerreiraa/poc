package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.factory.PessoaFactory;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:54 PM
 */
@RunWith(JUnit4.class)
public class Mapper5Test {

    private Mapper5 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper5.INSTANCE;
    }

    @Test
    public void toDTOS() {
        List<Pessoa> pessoas = PessoaFactory.pessoas1(5);
        List<PessoaDTO> pessoaDTOS = mapper.toDTOS(pessoas);

        for (int i = 0; i < 5; i++) {
            Pessoa pessoa = pessoas.get(i);
            PessoaDTO pessoaDTO = pessoaDTOS.get(i);

            Assertions.assertThat(pessoaDTO)
                    .isNotNull();

            Assertions.assertThat(pessoaDTO.getNome())
                    .isNotNull()
                    .isEqualTo(pessoa.getPrimeiroNome());

            Assertions.assertThat(pessoaDTO.getIdade())
                    .isNotNull()
                    .isEqualTo(pessoa.getIdade().intValue());

            Assertions.assertThat(pessoaDTO.getPeso().doubleValue())
                    .isNotNull()
                    .isEqualTo(pessoa.getPeso());

            Assertions.assertThat(pessoaDTO.getTelefonePrincipal())
                    .isNotNull()
                    .isEqualTo(pessoa.getTelefone1());

            Assertions.assertThat(pessoaDTO.getTelefoneOpcional())
                    .isNull();

            Assertions.assertThat(pessoaDTO.getDataDeCriacao())
                    .isEqualTo(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pessoa.getDataDeCriacao()));

            Assertions.assertThat(pessoaDTO.getDataDeAtualizacao())
                    .isEqualTo(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pessoa.getDataDeAtualizacao().getTime()));

            Assertions.assertThat(pessoaDTO.getDataDeNascimento())
                    .isEqualTo(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pessoa.getDataDeNascimento()));
        }
    }

}
