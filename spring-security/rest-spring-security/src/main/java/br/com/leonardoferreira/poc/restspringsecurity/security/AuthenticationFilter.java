package br.com.leonardoferreira.poc.restspringsecurity.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        final String authToken = request.getHeader("X-Auth-Token");
        if (!StringUtils.isEmpty(authToken)) {
            final PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(authToken, null);

            final Authentication authenticateResult;
            try {
                authenticateResult = authenticationManager.authenticate(authentication);
            } catch (final PreAuthenticatedCredentialsNotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }

            if (authenticateResult == null || !authenticateResult.isAuthenticated()) {
                throw new RuntimeException("Internal Authentication Service Error");
            }

            SecurityContextHolder.getContext().setAuthentication(authenticateResult);
        }

        chain.doFilter(request, response);
    }

}
