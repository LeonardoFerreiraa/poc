package br.com.leonardoferreira.test.features;

import br.com.leonardoferreira.test.Application;
import br.com.leonardoferreira.test.TestConfig;
import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.domain.Phone;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class, TestConfig.class}, loader = SpringBootContextLoader.class)
public class CadastroDeContato {

    @Autowired
    private ContactFactory contactFactory;

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

        System.out.println(response);
    }

}
