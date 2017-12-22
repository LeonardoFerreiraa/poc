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

import java.util.Arrays;
import java.util.List;

/**
 * Created by lferreira on 7/1/17.
 */
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactIndex {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Contact[]> response;

    private List<Contact> contacts;

    @Dado("contatos salvo no banco de dados")
    public void contatosSalvoNoBancoDeDados() {
        contacts = contactFactory.create(5);
    }

    @Quando("o usuário realiza a requisição para a listagem de contatos")
    public void oUsuárioRealizaARequisiçãoParaAListagemDeContatos() {
        response = restTemplate.getForEntity("/api/v1/contacts", Contact[].class);
    }

    @Então("o sistema deve retornar todos os contatos cadastrados")
    public void oSistemaDeveRetornarTodosOsContatosCadastrados() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Contact> body = Arrays.asList(response.getBody());
        Assertions.assertThat(body)
            .isNotNull()
            .isNotEmpty()
            .isEqualTo(contacts);
    }

}
