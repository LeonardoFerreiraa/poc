package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.factory.PessoaFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:27 PM
 */
@RunWith(JUnit4.class)
public class Mapper4Test {
    private Mapper4 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper4.INSTANCE;
    }

    @Test
    public void toDTO() {
        Pessoa pessoa = PessoaFactory.pessoa1();
        PessoaDTO pessoaDTO = mapper.toDTO(pessoa);

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
                .isEqualTo(DateTimeFormatter.ofPattern( "dd/MM/yyyy" ).format(pessoa.getDataDeNascimento()));
    }

    @Test
    public void toEntity() {
        PessoaDTO pessoaDTO = mapper.toDTO(PessoaFactory.pessoa1());
        Pessoa pessoa = mapper.toEntity(pessoaDTO);

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

        Assertions.assertThat(pessoa.getDataDeCriacao())
                .isNotNull();

        Assertions.assertThat(pessoa.getDataDeAtualizacao())
                .isNotNull();

        Assertions.assertThat(pessoa.getDataDeNascimento())
                .isNotNull();
    }

    @Test
    public void decimalFormatter() {
        BigDecimal b1 = new BigDecimal("10.00");
        BigDecimal b2 = new BigDecimal("10.90");
        BigDecimal b3 = new BigDecimal("20.987456");

        List<BigDecimal> bigDecimals = Arrays.asList(b1, b2, b3);

        List<String> strings = mapper.decimalFormatter(bigDecimals);
        Assertions.assertThat(strings)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .isEqualTo(Arrays.asList("$10.00", "$10.90", "$20.99"));
    }
}
