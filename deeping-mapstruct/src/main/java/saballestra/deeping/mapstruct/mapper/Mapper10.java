package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.mapper.util.PessoaDecorator;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:36 AM
 */
@Mapper
@DecoratedWith(PessoaDecorator.class)
public interface Mapper10 {

    Mapper10 INSTANCE = Mappers.getMapper(Mapper10.class);

    @Mappings({
            @Mapping(target = "nome", source = "primeiroNome"),
            @Mapping(target = "telefonePrincipal", source = "telefone1")
    })
    PessoaDTO toFullDTO(Pessoa pessoa);
}
