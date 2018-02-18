package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author s2it_leferreira
 * @since 1/31/18 2:22 PM
 */
@Mapper
public interface Mapper4 {

    Mapper4 INSTANCE = Mappers.getMapper(Mapper4.class);

    @Mappings({
        @Mapping(target = "nome", source = "primeiroNome"),
        @Mapping(target = "telefonePrincipal", source = "telefone1"),
        @Mapping(target = "dataDeCriacao", dateFormat = "dd/MM/yyyy HH:mm"),
        @Mapping(target = "dataDeAtualizacao", dateFormat = "dd/MM/yyyy HH:mm"),
        @Mapping(target = "dataDeNascimento", dateFormat = "dd/MM/yyyy")
    })
    PessoaDTO toDTO(Pessoa pessoa);

    @InheritInverseConfiguration
    Pessoa toEntity(PessoaDTO pessoaDTO);

    @IterableMapping(numberFormat = "$#.00")
    List<String> decimalFormatter(List<BigDecimal> decimalList);

}
