package br.com.leonardoferreira.poc.restspringsecurity.security.authenticationprovider;

import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import br.com.leonardoferreira.poc.restspringsecurity.service.AccountService;
import br.com.leonardoferreira.poc.restspringsecurity.service.TokenService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public LoginAuthenticationProvider(final AccountService accountService,
                                       final TokenService tokenService,
                                       final PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @SneakyThrows
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final Account account = accountService.findByUsername((String) authentication.getPrincipal());

        if (!passwordEncoder.matches((CharSequence) authentication.getCredentials(), account.getPassword())) {
            throw new BadCredentialsException("Password doesn't match");
        }

        final UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                account,
                account.getPassword(),
                account.getAuthorities()
        );
        user.setDetails(tokenService.encode(account));

        return user;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
