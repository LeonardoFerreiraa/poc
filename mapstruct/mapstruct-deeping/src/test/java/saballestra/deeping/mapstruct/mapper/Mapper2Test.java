package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.domain.embedded.Telefone;
import saballestra.deeping.mapstruct.factory.PessoaFactory;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:00 PM
 */
@RunWith(JUnit4.class)
public class Mapper2Test {

    private Mapper2 mapper;

    @Before
    public void setup() {
        mapper = Mapper2.INSTANCE;
    }

    @Test
    public void toDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        Telefone telefone1 = PessoaFactory.telefone();
        Telefone telefone2 = PessoaFactory.telefone();

        PessoaDTO pessoaDTO = mapper.toDTO(pessoa, telefone1, telefone2);

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
                .isEqualTo(telefone1);

        Assertions.assertThat(pessoaDTO.getTelefoneOpcional())
                .isNotNull()
                .isEqualTo(telefone2.getNumero());
    }
}
