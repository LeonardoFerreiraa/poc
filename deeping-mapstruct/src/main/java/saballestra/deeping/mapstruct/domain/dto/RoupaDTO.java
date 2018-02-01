package saballestra.deeping.mapstruct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saballestra.deeping.mapstruct.domain.TamanhoSigla;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:20 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoupaDTO {

    private String cor;

    private TamanhoSigla tamanho;

}
