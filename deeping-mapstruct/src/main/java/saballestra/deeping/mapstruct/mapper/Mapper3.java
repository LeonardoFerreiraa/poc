package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:06 PM
 */
@Mapper
public interface Mapper3 {
    Mapper3 INSTANCE = Mappers.getMapper(Mapper3.class);

    void updateDTO(Pessoa pessoa, @MappingTarget PessoaDTO pessoaDTO);

    @Mappings({
            @Mapping(target = "nome", source = "primeiroNome"),
            @Mapping(target = "telefonePrincipal", source = "telefone1"),
    })
    void updateFullDTO(Pessoa pessoa, @MappingTarget PessoaDTO pessoaDTO);

    PessoaDTO updateDTOWithReturn(Pessoa pessoa, @MappingTarget PessoaDTO pessoaDTO);

    @InheritInverseConfiguration(name = "updateFullDTO")
    void updateFullEntity(@MappingTarget Pessoa pessoa, PessoaDTO pessoaDTO);

}
