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
 * @since 1/31/18 11:57 AM
 */
@RunWith(JUnit4.class)
public class Mapper1Test {

    private Mapper1 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper1.INSTANCE;
    }

    @Test
    public void toDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = mapper.toDTO(pessoa);

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
    public void toFullDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = mapper.toFullDTO(pessoa);

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
    public void toEntity() {
        PessoaDTO pessoaDTO = mapper.toDTO(PessoaFactory.pessoa1());
        Pessoa pessoa = mapper.toEntity(pessoaDTO);

        Assertions.assertThat(pessoa)
                .isNotNull();

        Assertions.assertThat(pessoa.getPrimeiroNome())
                .isNull();

        Assertions.assertThat(pessoa.getIdade().intValue())
                .isNotNull()
                .isEqualTo(pessoaDTO.getIdade());

        Assertions.assertThat(pessoa.getPeso())
                .isNotNull()
                .isEqualTo(pessoaDTO.getPeso().doubleValue());

        Assertions.assertThat(pessoa.getTelefone1())
                .isNull();

        Assertions.assertThat(pessoa.getTelefone2())
                .isNull();
    }

    @Test
    public void toFullEntity() {
        PessoaDTO pessoaDTO = mapper.toFullDTO(PessoaFactory.pessoa1());
        Pessoa pessoa = mapper.toFullEntity(pessoaDTO);

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