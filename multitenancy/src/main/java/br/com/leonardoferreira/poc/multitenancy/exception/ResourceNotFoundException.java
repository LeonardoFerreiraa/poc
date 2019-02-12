package br.com.leonardoferreira.poc.multitenancy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3449562280919192762L;
}