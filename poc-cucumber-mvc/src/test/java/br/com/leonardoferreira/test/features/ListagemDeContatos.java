package br.com.leonardoferreira.test.features;

import br.com.leonardoferreira.test.Application;
import br.com.leonardoferreira.test.TestConfig;
import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.factory.ContactFactory;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class, TestConfig.class}, loader = SpringBootContextLoader.class)
public class ListagemDeContatos {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private MockMvc mockMvc;

    private MvcResult request;

    private List<Contact> contacts;

    @Dado("^contatos salvo no banco de dados$")
    public void contatosSalvoNoBancoDeDados() throws Throwable {
        contacts = contactFactory.create(10);
    }

    @Quando("^o usuário realiza a requisição para a listagem de contatos$")
    public void oUsuarioRealizaARequisicaoParaAListagemDeContatos() throws Throwable {
        request = mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @SuppressWarnings("unchecked")
    @Entao("^o sistema deve retornar todos os contatos cadastrados$")
    public void oSistemaDeveRetornarTodosOsContatosCadastrados() throws Throwable {
        Assertions.assertThat(request).isNotNull();
        ModelAndView modelAndView = request.getModelAndView();
        Assertions.assertThat(modelAndView.getViewName()).isEqualTo("contacts/index");
        List<Contact> contacts = (List<Contact>) modelAndView.getModel().get("contacts");
        Assertions.assertThat(contacts)
                .isNotNull()
                .isNotEmpty()
                .hasSize(this.contacts.size());
        Assertions.assertThat(this.contacts.stream().map(Contact::getId).collect(Collectors.toList()))
                .isEqualTo(contacts.stream().map(Contact::getId).collect(Collectors.toList()));
    }
}
