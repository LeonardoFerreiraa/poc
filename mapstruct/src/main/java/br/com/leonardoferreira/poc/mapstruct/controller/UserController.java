package br.com.leonardoferreira.poc.mapstruct.controller;

import br.com.leonardoferreira.poc.mapstruct.domain.dto.UserDTO;
import br.com.leonardoferreira.poc.mapstruct.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<UserDTO> index(Pageable pageable) {
       return userService.findAll(pageable);
    }
}
