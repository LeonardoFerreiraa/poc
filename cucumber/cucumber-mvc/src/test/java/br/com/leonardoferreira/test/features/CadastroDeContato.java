package br.com.leonardoferreira.test.features;

import br.com.leonardoferreira.test.Application;
import br.com.leonardoferreira.test.TestConfig;
import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.domain.Phone;
import br.com.leonardoferreira.test.exception.ContactNotFound;
import br.com.leonardoferreira.test.factory.AccountFactory;
import br.com.leonardoferreira.test.factory.ContactFactory;
import br.com.leonardoferreira.test.repository.ContactRepository;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class, TestConfig.class}, loader = SpringBootContextLoader.class)
public class CadastroDeContato {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MockMvc mockMvc;

    private Contact contact;

    private MvcResult response;

    @Dado("^que exista um contato sem todos os dados obrigatórios preenchidos$")
    public void queExistaUmContatoSemTodosOsDadosObrigatoriosPreenchidos() throws Throwable {
        contact = new Contact();
        contact.setPhone(new Phone());
    }

    @Quando("^for feita a requisição para o cadastro de um contato$")
    public void forFeitaARequisicaoParaOCadastroDeUmContato() throws Throwable {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/contacts");
        post.param("name", contact.getName());
        post.param("email", contact.getEmail());
        post.param("phone.number", contact.getPhone().getNumber());
        post.with(accountFactory.authenticatedUser());
        response = mockMvc.perform(post).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Entao("^o sistema deve retornar erros de validação para o cadastro de contato$")
    public void oSistemaDeveRetornarErrosDeValidacaoParaOCadastroDeContato() throws Throwable {
        Assertions.assertThat(response).isNotNull();
        ModelAndView modelAndView = response.getModelAndView();

        BeanPropertyBindingResult result = (BeanPropertyBindingResult) modelAndView.getModel()
                .get("org.springframework.validation.BindingResult.contact");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getObjectName()).isEqualTo("contact");

        Assertions.assertThat(result.getAllErrors())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Dado("^que exista um contato com todos os dados obrigatórios preenchidos$")
    public void queExistaUmContatoComTodosOsDadosObrigatoriosPreenchidos() throws Throwable {
        contact = contactFactory.build();
    }

    @Então("^o sistema deve cadastrar o contato com sucesso$")
    public void oSistemaDeveCadastrarOContatoComSucesso() throws Throwable {
        Assertions.assertThat(response).isNotNull();
        ModelAndView modelAndView = response.getModelAndView();

        Assertions.assertThat(modelAndView.getViewName())
                .isEqualTo("redirect:/contacts");

        Assertions.assertThat(contactRepository.count())
                .isEqualTo(1);
        Contact dbContact = contactRepository.findById(1L)
                .orElseThrow(ContactNotFound::new);

        Assertions.assertThat(dbContact.getName())
                .isEqualTo(contact.getName());

        Assertions.assertThat(dbContact.getEmail())
                .isEqualTo(contact.getEmail());

        Assertions.assertThat(dbContact.getAnswerableUser())
                .isEqualTo(accountFactory.getCurrentUser().getUsername());

    }
}
