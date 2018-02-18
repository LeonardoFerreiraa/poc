package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Roupa;
import saballestra.deeping.mapstruct.domain.dto.RoupaDTO;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:21 AM
 */
@Mapper(uses = Mapper8.class)
public interface Mapper9 {

    Mapper9 INSTANCE = Mappers.getMapper(Mapper9.class);

    RoupaDTO toDTO(Roupa roupa);

    Roupa toEntity(RoupaDTO roupaDTO);

}
