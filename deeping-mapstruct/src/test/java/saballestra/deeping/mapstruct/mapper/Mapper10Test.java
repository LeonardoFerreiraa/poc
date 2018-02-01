package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.factory.PessoaFactory;
import saballestra.deeping.mapstruct.mapper.util.PessoaDecorator;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:39 AM
 */
@RunWith(JUnit4.class)
public class Mapper10Test {
    private Mapper10 mapper;

    @Before
    public void setup() {
        this.mapper = new PessoaDecorator(Mapper10.INSTANCE);
    }

    @Test
    public void toFullDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = mapper.toFullDTO(pessoa);

        Assertions.assertThat(pessoaDTO.getNome())
                .isEqualTo(pessoa.getPrimeiroNome() + " " + pessoa.getSobreNome());

        Assertions.assertThat(pessoaDTO.getTelefoneOpcional())
                .isEqualTo(pessoa.getTelefone2().getDdd() + " " + pessoa.getTelefone2().getNumero());

        Assertions.assertThat(pessoaDTO.getIdade())
                .isNotNull();

        Assertions.assertThat(pessoaDTO.getPeso())
                .isNotNull();
    }
}
