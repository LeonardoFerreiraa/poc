package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.Tamanho;
import saballestra.deeping.mapstruct.domain.TamanhoSigla;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:13 AM
 */
@RunWith(JUnit4.class)
public class Mapper8Test {
    private Mapper8 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper8.INSTANCE;
    }

    @Test
    public void toSigla() {
        TamanhoSigla p = mapper.toSigla(Tamanho.PEQUENO);
        TamanhoSigla m = mapper.toSigla(Tamanho.MEDIO);
        TamanhoSigla g = mapper.toSigla(Tamanho.GRANDE);

        Assertions.assertThat(p)
                .isEqualTo(TamanhoSigla.P);
        Assertions.assertThat(m)
                .isEqualTo(TamanhoSigla.M);
        Assertions.assertThat(g)
                .isEqualTo(TamanhoSigla.G);
    }

    @Test
    public void fromSigla() {
        Tamanho pequeno = mapper.fromSigla(TamanhoSigla.P);
        Tamanho medio = mapper.fromSigla(TamanhoSigla.M);
        Tamanho grande = mapper.fromSigla(TamanhoSigla.G);

        Assertions.assertThat(pequeno)
                .isEqualTo(Tamanho.PEQUENO);
        Assertions.assertThat(medio)
                .isEqualTo(Tamanho.MEDIO);
        Assertions.assertThat(grande)
                .isEqualTo(Tamanho.GRANDE);
    }

}
