package br.com.leonardoferreira.report.security.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {
    private Account account;
}
