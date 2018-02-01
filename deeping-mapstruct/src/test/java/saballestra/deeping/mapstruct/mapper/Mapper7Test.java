package saballestra.deeping.mapstruct.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import saballestra.deeping.mapstruct.domain.dto.FilmeDTO;
import saballestra.deeping.mapstruct.domain.Filme;
import saballestra.deeping.mapstruct.factory.FilmeFactory;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:06 AM
 */
@RunWith(JUnit4.class)
public class Mapper7Test {
    private Mapper7 mapper;

    @Before
    public void setup() {
        this.mapper = Mapper7.INSTANCE;
    }

    @Test
    public void toDTO() {
        Filme filme = FilmeFactory.filme1();
        FilmeDTO filmeDTO = mapper.toDTO(filme);
        Assertions.assertThat(filmeDTO.getTitulo())
                .isEqualTo(filme.getTitulo().toLowerCase());
    }

    public void toEntity() {
        FilmeDTO filmeDTO = mapper.toDTO(FilmeFactory.filme1());
        Filme filme = mapper.toEntity(filmeDTO);

        Assertions.assertThat(filme.getTitulo())
                .isEqualTo(filmeDTO.getTitulo().toUpperCase());
    }
}
