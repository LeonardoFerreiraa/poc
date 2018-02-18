package br.com.leonardoferreira.hello.feature.contact;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.factory.ContactFactory;
import br.com.leonardoferreira.hello.repository.ContactRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by lferreira on 7/1/17.
 */
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteContact {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact;

    @Dado("um contato já salvo no banco de dados pronto para remoção")
    public void umContatoJáSalvoNoBancoDeDadosProntoParaRemoção() {
        contact = contactFactory.create();
    }

    @Quando("é feita a requisição para remoção do contato")
    public void éFeitaARequisiçãoParaRemoçãoDoContato() {
        Assertions.assertThat(contactRepository.count()).isEqualTo(1);
        restTemplate.delete("/api/v1/contacts/1");
    }

    @Então("o contato é removido do banco de dados")
    public void oContatoÉRemovidoDoBancoDeDados() {
        Assertions.assertThat(contactRepository.count()).isZero();
    }

}
