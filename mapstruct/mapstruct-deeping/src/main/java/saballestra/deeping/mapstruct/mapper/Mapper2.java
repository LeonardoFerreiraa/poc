package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.domain.embedded.Telefone;

/**
 * @author s2it_leferreira
 * @since 1/31/18 1:58 PM
 */
@Mapper
public interface Mapper2 {
    Mapper2 INSTANCE = Mappers.getMapper(Mapper2.class);

    @Mappings({
            @Mapping(target = "nome", source = "pessoa.primeiroNome"),
            @Mapping(target = "telefonePrincipal", source = "telefone"),
            @Mapping(target = "telefoneOpcional", source = "telefone2.numero")
    })
    PessoaDTO toDTO(Pessoa pessoa, Telefone telefone, Telefone telefone2);
}
