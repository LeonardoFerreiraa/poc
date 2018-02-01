package saballestra.deeping.mapstruct.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;
import saballestra.deeping.mapstruct.domain.Tamanho;
import saballestra.deeping.mapstruct.domain.TamanhoSigla;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:11 AM
 */
@Mapper
public interface Mapper8 {
    Mapper8 INSTANCE = Mappers.getMapper(Mapper8.class);

    @ValueMappings({
            @ValueMapping(target = "P", source = "PEQUENO"),
            @ValueMapping(target = "M", source = "MEDIO"),
            @ValueMapping(target = "G", source = "GRANDE")
    })
    TamanhoSigla toSigla(Tamanho tamanho);

    @InheritInverseConfiguration
    Tamanho fromSigla(TamanhoSigla tamanhoSigla);


}
