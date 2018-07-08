package br.com.leonardoferreira.poc.mapper;

import br.com.leonardoferreira.poc.batch.BatchImportItem;
import br.com.leonardoferreira.poc.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mappings({
            @Mapping(target = "id",                 ignore = true),
            @Mapping(target = "idOfExternalSystem", source = "externalId"),
            @Mapping(target = "nameOfSomething",    source = "customStr")
    })
    Item itemFromBatchImportItem(BatchImportItem batchImportItem);
}
