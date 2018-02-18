package br.com.leonardoferreira.test.controller;

import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ModelAndView index() {
        List<Contact> contacts = contactService.findAll();
        return new ModelAndView("contacts/index")
                .addObject("contacts", contacts);
    }

    @GetMapping("/new")
    public ModelAndView preCreate() {
        return new ModelAndView("contacts/create")
                .addObject("contact", new Contact());
    }

    @PostMapping
    public ModelAndView create(@Validated Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("contacts/create")
                    .addObject("contact", contact);
        }

        contactService.create(contact);

        return new ModelAndView("redirect:/contacts");
    }
}
