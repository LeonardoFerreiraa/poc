package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;

import java.util.List;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:53 PM
 */
@Mapper(uses = Mapper4.class)
public interface Mapper5 {

    Mapper5 INSTANCE = Mappers.getMapper(Mapper5.class);

    List<PessoaDTO> toDTOS(List<Pessoa> pessoas);

}
