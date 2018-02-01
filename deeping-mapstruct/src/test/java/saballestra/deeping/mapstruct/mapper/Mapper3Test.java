package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.factory.PessoaFactory;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:07 PM
 */
@RunWith(JUnit4.class)
public class Mapper3Test {

    private Mapper3 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper3.INSTANCE;
    }

    @Test
    public void updateDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = new PessoaDTO();

        mapper.updateDTO(pessoa, pessoaDTO);

        Assertions.assertThat(pessoaDTO)
                .isNotNull();

        Assertions.assertThat(pessoaDTO.getNome())
                .isNull();

        Assertions.assertThat(pessoaDTO.getIdade())
                .isNotNull()
                .isEqualTo(pessoa.getIdade().intValue());

        Assertions.assertThat(pessoaDTO.getPeso().doubleValue())
                .isNotNull()
                .isEqualTo(pessoa.getPeso());

        Assertions.assertThat(pessoaDTO.getTelefonePrincipal())
                .isNull();

        Assertions.assertThat(pessoaDTO.getTelefoneOpcional())
                .isNull();
    }

    @Test(expected = NullPointerException.class)
    public void updateDTONull() {
        Pessoa pessoa = PessoaFactory.pessoa1();

        mapper.updateDTO(pessoa, null);
    }

    @Test
    public void updateDTOWithReturn() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = new PessoaDTO();

        PessoaDTO pessoaDTOResult = mapper.updateDTOWithReturn(pessoa, pessoaDTO);

        Assertions.assertThat(pessoaDTO == pessoaDTOResult)
                .isTrue();

        Assertions.assertThat(pessoaDTO)
                .isNotNull();

        Assertions.assertThat(pessoaDTO.getNome())
                .isNull();

        Assertions.assertThat(pessoaDTO.getIdade())
                .isNotNull()
                .isEqualTo(pessoa.getIdade().intValue());

        Assertions.assertThat(pessoaDTO.getPeso().doubleValue())
                .isNotNull()
                .isEqualTo(pessoa.getPeso());

        Assertions.assertThat(pessoaDTO.getTelefonePrincipal())
                .isNull();

        Assertions.assertThat(pessoaDTO.getTelefoneOpcional())
                .isNull();
    }

    @Test
    public void updateFullDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = new PessoaDTO();
        mapper.updateFullDTO(pessoa, pessoaDTO);

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
    }
    @Test
    public void toFullEntity() {
        PessoaDTO pessoaDTO = Mapper1.INSTANCE.toFullDTO(PessoaFactory.pessoa1());
        Pessoa pessoa = new Pessoa();
        mapper.updateFullEntity(pessoa, pessoaDTO);

        Assertions.assertThat(pessoa)
                .isNotNull();

        Assertions.assertThat(pessoa.getPrimeiroNome())
                .isNotNull()
                .isEqualTo(pessoaDTO.getNome());

        Assertions.assertThat(pessoa.getIdade().intValue())
                .isNotNull()
                .isEqualTo(pessoaDTO.getIdade());

        Assertions.assertThat(pessoa.getPeso())
                .isNotNull()
                .isEqualTo(pessoaDTO.getPeso().doubleValue());

        Assertions.assertThat(pessoa.getTelefone1())
                .isNotNull()
                .isEqualTo(pessoaDTO.getTelefonePrincipal());

        Assertions.assertThat(pessoa.getTelefone2())
                .isNull();
    }
}
