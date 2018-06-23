package br.com.leonardoferreira.hello.feature.contact;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.factory.ContactFactory;
import br.com.leonardoferreira.hello.repository.ContactRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by lferreira on 7/1/17.
 */
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateContact {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact;

    private Contact dbContact;

    private ResponseEntity<Map> response;

    @Dado("dados validos para alteração de um contato")
    public void dadosValidosParaAlteraçãoDeUmContato() {
        contact = contactFactory.build();
    }

    @E("um contato já salvo no banco de dados pronto para alteração")
    public void umContatoJáSalvoNoBancoDeDadosProntoParaAlteração() {
        dbContact = contactFactory.create();
        contact.setId(dbContact.getId());
    }

    @Quando("o usuário faz uma requisição para alteração de contato")
    public void oUsuárioFazUmaRequisiçãoParaAlteraçãoDeContato() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Contact> entity = new HttpEntity<Contact>(contact, headers);
        response = restTemplate.exchange("/api/v1/contacts", HttpMethod.PUT, entity, Map.class);
    }

    @Então("o contato é atualizado no banco de dados")
    public void oContatoÉAtualizadoNoBancoDeDados() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Contact dbContact = contactRepository.findById(1L).orElse(null);
        Assertions.assertThat(dbContact).isNotNull();
        Assertions.assertThat(dbContact.getName()).isEqualTo(contact.getName());
        Assertions.assertThat(dbContact.getEmail()).isEqualTo(contact.getEmail());
        Assertions.assertThat(dbContact.getPhone()).isEqualTo(contact.getPhone());
    }

    @Dado("dados invalidos para alteração de um contato")
    public void dadosInvalidosParaAlteraçãoDeUmContato() {
        contact = new Contact();
        contact.setEmail("invalid");
    }

    @SuppressWarnings("unchecked")
    @Então("o sistema deve retornar erros de validacao para a alteração de contato")
    public void oSistemaDeveRetornarErrosDeValidacaoParaAAlteraçãoDeContato() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Contact one = contactRepository.findById(1L).orElse(null);
        Assertions.assertThat(one).isNotNull();
        Assertions.assertThat(dbContact.getName()).isEqualTo(one.getName());
        Assertions.assertThat(dbContact.getEmail()).isEqualTo(one.getEmail());
        Assertions.assertThat(dbContact.getPhone()).isEqualTo(one.getPhone());

        List<Map> errors = (List<Map>) response.getBody().get("errors");
        Assertions.assertThat(errors).isNotEmpty();
        Assertions.assertThat(errors.size()).isEqualTo(2);

        errors.forEach(error -> {
            if ("name".equals(error.get("field"))) {
                Assertions.assertThat(error.get("defaultMessage"))
                    .isEqualTo("Nome não pode ser vazio.");
            } else if ("email".equals(error.get("field"))) {
                Assertions.assertThat(error.get("defaultMessage"))
                    .isEqualTo("Email deve ser um endereço de email valido.");
            } else {
                Assertions.fail("Field não esperado.");
            }
        });
    }
}
