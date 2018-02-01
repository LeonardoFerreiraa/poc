package saballestra.deeping.mapstruct.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author s2it_leferreira
 * @since 1/31/18 11:44 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    private String ddd;

    private String numero;

}
