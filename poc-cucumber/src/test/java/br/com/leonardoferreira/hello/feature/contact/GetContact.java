package br.com.leonardoferreira.hello.feature.contact;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.factory.ContactFactory;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by lferreira on 7/1/17.
 */
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetContact {
    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Contact> response;

    private Contact contact;

    @Dado("contato salvo no banco de dados")
    public void contatoSalvoNoBancoDeDados() {
        contact = contactFactory.create();
    }

    @Quando("o usuário realiza a requisição para a busca de contato")
    public void oUsuárioRealizaARequisiçãoParaABuscaDeContato() {
        response = restTemplate.getForEntity("/api/v1/contacts/1", Contact.class);
    }

    @Então("o sistema deve retorna o usuário cadastrado no banco de dados")
    public void oSistemaDeveRetornaOUsuárioCadastradoNoBancoDeDados() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(contact.getName());
        Assertions.assertThat(response.getBody().getPhone()).isEqualTo(contact.getPhone());
        Assertions.assertThat(response.getBody().getEmail()).isEqualTo(contact.getEmail());
        Assertions.assertThat(response.getBody().getId()).isEqualTo(1);
    }

    @Então("o sistema deve retornar mensagem de erro dizendo que o contato não existe")
    public void oSistemaDeveRetornarMensagemDeErroDizendoQueOContatoNãoExiste() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
