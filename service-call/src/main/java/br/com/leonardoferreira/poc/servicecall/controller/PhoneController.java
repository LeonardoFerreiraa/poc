package br.com.leonardoferreira.poc.servicecall.controller;

import br.com.leonardoferreira.poc.servicecall.domain.request.PhoneRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.PhoneResponse;
import br.com.leonardoferreira.poc.servicecall.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public List<PhoneResponse> findAll() {
        return phoneService.findAll();
    }

    @GetMapping("/{id}")
    public PhoneResponse findById(@PathVariable Long id) {
        return phoneService.findById(id);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody PhoneRequest phoneRequest) {
        Long id = phoneService.created(phoneRequest);
        return ResponseEntity.created(URI.create("/phones/" + id)).build();
    }

    @GetMapping("/contains")
    public Boolean contains(PhoneRequest phoneRequest) {
        return phoneService.containsPhone(phoneRequest);
    }
}
