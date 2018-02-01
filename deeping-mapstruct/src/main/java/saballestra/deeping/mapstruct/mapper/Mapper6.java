package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;

/**
 * @author s2it_leferreira
 * @since 2/1/18 10:30 AM
 */
@Mapper
public interface Mapper6 {

    Mapper6 INSTANCE = Mappers.getMapper(Mapper6.class);

    @Mappings({
            @Mapping(target = "nome", expression = "java(pessoa.getPrimeiroNome() + \" \" + pessoa.getSobreNome())"),
            @Mapping(target = "idade", constant = "22"),
            @Mapping(target = "dataDeCriacao", defaultValue = "26/12/1995 10:40", dateFormat = "dd/MM/yyyy HH:mm"),
            @Mapping(target = "telefoneOpcional", ignore = true)
    })
    PessoaDTO toDTO(Pessoa pessoa);

}
