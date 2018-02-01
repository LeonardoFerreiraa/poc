package saballestra.deeping.mapstruct.factory;
import com.github.javafaker.Faker;
import saballestra.deeping.mapstruct.domain.embedded.Telefone;

import saballestra.deeping.mapstruct.domain.Pessoa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author s2it_leferreira
 * @since 1/31/18 12:00 PM
 */
public class PessoaFactory {

    public static Pessoa pessoa1() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        Pessoa pessoa = new Pessoa();

        pessoa.setPrimeiroNome(faker.name().firstName());
        pessoa.setSobreNome(faker.name().lastName());
        pessoa.setIdade(faker.number().numberBetween(18L, 40L));
        pessoa.setPeso(faker.number().randomDouble(2, 80, 100));
        pessoa.setTelefone1(telefone());
        pessoa.setTelefone2(telefone());
        pessoa.setDataDeCriacao(new Date());
        pessoa.setDataDeAtualizacao(Calendar.getInstance());
        pessoa.setDataDeNascimento(LocalDate.of(1995, 12, 26));

        return pessoa;
    }

    public static List<Pessoa> pessoas1(int qtd) {
        return IntStream.range(0, qtd).boxed()
                .map(i -> PessoaFactory.pessoa1())
                .collect(Collectors.toList());
    }

    public static Telefone telefone() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        return new Telefone(faker.number().digits(2), faker.phoneNumber().phoneNumber());
    }



}
