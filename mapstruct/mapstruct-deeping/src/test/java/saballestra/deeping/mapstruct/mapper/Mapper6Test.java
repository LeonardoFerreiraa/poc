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

/**
 * @author s2it_leferreira
 * @since 2/1/18 10:37 AM
 */
@RunWith(JUnit4.class)
public class Mapper6Test {

    private Mapper6 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper6.INSTANCE;
    }

    @Test
    public void toDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        pessoa.setDataDeCriacao(null);
        PessoaDTO pessoaDTO = mapper.toDTO(pessoa);

        Assertions.assertThat(pessoaDTO)
                .isNotNull();

        Assertions.assertThat(pessoaDTO.getNome())
                .isNotNull()
                .isEqualTo(pessoa.getPrimeiroNome() + " " + pessoa.getSobreNome());

        Assertions.assertThat(pessoaDTO.getIdade())
                .isEqualTo(22);

        Assertions.assertThat(pessoaDTO.getDataDeCriacao())
                .isNotNull()
                .isEqualTo("26/12/1995 10:40");
    }

}
