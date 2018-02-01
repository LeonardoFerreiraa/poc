package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Roupa;
import saballestra.deeping.mapstruct.domain.Tamanho;
import saballestra.deeping.mapstruct.domain.TamanhoSigla;
import saballestra.deeping.mapstruct.domain.dto.RoupaDTO;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:22 AM
 */
@RunWith(JUnit4.class)
public class Mapper9Test {
    private Mapper9 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper9.INSTANCE;
    }

    @Test
    public void toDTO() {
        Roupa camisa = new Roupa("preta", Tamanho.GRANDE);
        RoupaDTO camisaDTO = mapper.toDTO(camisa);

        Assertions.assertThat(camisaDTO.getCor())
                .isEqualTo("preta");

        Assertions.assertThat(camisaDTO.getTamanho())
                .isEqualTo(TamanhoSigla.G);
    }

    @Test
    public void toEntity() {
        RoupaDTO camisaDTO = new RoupaDTO("preta", TamanhoSigla.G);
        Roupa camisa = mapper.toEntity(camisaDTO);

        Assertions.assertThat(camisa.getCor())
                .isEqualTo("preta");

        Assertions.assertThat(camisa.getTamanho())
                .isEqualTo(Tamanho.GRANDE);
    }
}
