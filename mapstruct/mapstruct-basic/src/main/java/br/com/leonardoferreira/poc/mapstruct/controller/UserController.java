package br.com.leonardoferreira.poc.mapstruct.controller;

import br.com.leonardoferreira.poc.mapstruct.domain.response.UserResponse;
import br.com.leonardoferreira.poc.mapstruct.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> findAll(final Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable final Long id) {
        return userService.findById(id);
    }

}
