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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CreateContact {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact;

    private ResponseEntity<Map> response;

    @Dado("dados validos para cadastro de contato")
    public void dadosValidosParaCadastroDeContato() {
        contact = contactFactory.build();
    }

    @Quando("o usuário faz uma requisição para cadastro de contato")
    public void oUsuárioFazUmaRequisiçãoParaCadastroDeContato() {
        Assertions.assertThat(contactRepository.count()).isEqualTo(0);
        response = restTemplate.postForEntity("/api/v1/contacts", contact, Map.class);
    }

    @Então("o contato é salvo no banco de dados")
    public void oContatoÉSalvoNoBancoDeDados() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(contactRepository.count()).isEqualTo(1);
        final Contact dbContact = contactRepository.findById(1L).orElse(null);
        Assertions.assertThat(dbContact).isNotNull();
        Assertions.assertThat(dbContact.getName()).isEqualTo(contact.getName());
        Assertions.assertThat(dbContact.getEmail()).isEqualTo(contact.getEmail());
        Assertions.assertThat(dbContact.getPhone()).isEqualTo(contact.getPhone());
        Assertions.assertThat(dbContact.getId()).isNotNull();
        Assertions.assertThat(dbContact.getCreatedAt()).isNotNull();
        Assertions.assertThat(dbContact.getUpdatedAt()).isNotNull();
    }

    @Dado("dados invalidos para cadastro de contato")
    public void dadosInvalidosParaCadastroDeContato() {
        contact = new Contact();
        contact.setEmail("not valid");
    }

    @SuppressWarnings("unchecked")
    @Então("o sistema deve retornar erros de validacao para o cadastro de contato")
    public void oSistemaDeveRetornarErrosDeValidacaoParaOCadastroDeContato() {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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
