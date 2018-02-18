package br.com.leonardoferreira.poc.mapstruct.mapper.converter;

import br.com.leonardoferreira.poc.mapstruct.domain.entity.TitleEnum;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("TitleTranslator")
public class TitleTranslator {

    @Named("EnglishToPortuguese")
    public String translateTitleENToPT(TitleEnum titleEnum) {
        switch (titleEnum) {
            case DOCTOR:
                return "DOUTOR";
            case MASTER:
                return "MESTRE";
            default:
                throw new IllegalArgumentException("title not found");
        }
    }
}
