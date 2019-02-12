package br.com.leonardoferreira.poc.multitenancy.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    private final AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String authToken = request.getHeader("X-Auth-Token");
        if (StringUtils.isEmpty(authToken)) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(authToken, null);
            Authentication authenticateResult;
            try {
                authenticateResult = authenticationManager.authenticate(authentication);
            } catch (PreAuthenticatedCredentialsNotFoundException e) {
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