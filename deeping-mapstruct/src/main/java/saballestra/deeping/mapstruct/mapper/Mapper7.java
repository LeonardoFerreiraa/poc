package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.dto.FilmeDTO;
import saballestra.deeping.mapstruct.domain.Filme;
import saballestra.deeping.mapstruct.mapper.util.DecoradorDeTitulo;

/**
 * @author s2it_leferreira
 * @since 2/1/18 10:46 AM
 */
@Mapper(uses = DecoradorDeTitulo.class)
public interface Mapper7 {
    Mapper7 INSTANCE = Mappers.getMapper(Mapper7.class);

    @Mapping(target = "titulo", qualifiedByName = { "decoradorDeTitulo", "minusculo" })
    FilmeDTO toDTO(Filme filme);

    @Mapping(target = "titulo", qualifiedByName = { "decoradorDeTitulo", "maiusculo" })
    Filme toEntity(FilmeDTO filme);


}
