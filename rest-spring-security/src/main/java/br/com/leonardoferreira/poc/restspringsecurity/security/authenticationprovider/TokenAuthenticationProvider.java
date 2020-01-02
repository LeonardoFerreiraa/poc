package br.com.leonardoferreira.poc.restspringsecurity.security.authenticationprovider;

import br.com.leonardoferreira.poc.restspringsecurity.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenService tokenService;

    public TokenAuthenticationProvider(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        try {
            final UserDetails userDetails = tokenService.decode((String) authentication.getPrincipal());

            return new PreAuthenticatedAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        } catch (final Exception e) {
            throw new PreAuthenticatedCredentialsNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }

}
