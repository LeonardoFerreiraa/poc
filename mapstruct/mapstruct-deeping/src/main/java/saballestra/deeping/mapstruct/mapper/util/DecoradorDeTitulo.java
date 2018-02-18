package saballestra.deeping.mapstruct.mapper.util;

import org.mapstruct.Named;

/**
 * @author s2it_leferreira
 * @since 2/1/18 10:45 AM
 */
@Named("decoradorDeTitulo")
public class DecoradorDeTitulo {

    @Named("maiusculo")
    public String maiusculo(String titulo) {
        return titulo.toUpperCase();
    }

    @Named("minusculo")
    public String minusculo(String titulo) {
        return titulo.toLowerCase();
    }
}
