package saballestra.deeping.mapstruct.factory;

import com.github.javafaker.Faker;
import saballestra.deeping.mapstruct.domain.Filme;

import java.util.Locale;

/**
 * @author s2it_leferreira
 * @since 2/1/18 10:43 AM
 */
public class FilmeFactory {
    public static Filme filme1() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        Filme filme = new Filme();
        filme.setTitulo(faker.book().title());
        filme.setDuracao(faker.number().randomDouble(2, 50, 120));

        return filme;
    }
}
