package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;

/**
 * @author s2it_leferreira
 * @since 1/31/18 11:55 AM
 */
@Mapper
public interface Mapper1 {
    Mapper1 INSTANCE = Mappers.getMapper(Mapper1.class);

    PessoaDTO toDTO(Pessoa pessoa);

    @Mappings({
            @Mapping(target = "nome", source = "primeiroNome"),
            @Mapping(target = "telefonePrincipal", source = "telefone1")
    })
    PessoaDTO toFullDTO(Pessoa pessoa);

    @InheritInverseConfiguration(name = "toDTO")
    Pessoa toEntity(PessoaDTO pessoaDTO);

    @InheritInverseConfiguration(name = "toFullDTO")
    Pessoa toFullEntity(PessoaDTO pessoaDTO);

}
