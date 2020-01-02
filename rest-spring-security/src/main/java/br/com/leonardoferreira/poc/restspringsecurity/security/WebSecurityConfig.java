package br.com.leonardoferreira.poc.restspringsecurity.security;

import br.com.leonardoferreira.poc.restspringsecurity.security.authenticationprovider.LoginAuthenticationProvider;
import br.com.leonardoferreira.poc.restspringsecurity.security.authenticationprovider.TokenAuthenticationProvider;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginAuthenticationProvider loginAuthenticationProvider;

    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    public WebSecurityConfig(final LoginAuthenticationProvider loginAuthenticationProvider,
                             final TokenAuthenticationProvider tokenAuthenticationProvider) {
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(customizer -> {
                    customizer.anyRequest().authenticated();
                })
                .sessionManagement(customizer -> {
                    customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .formLogin(customizer -> {
                    customizer.successHandler(this::loginSuccessHandler);
                    customizer.failureHandler(this::loginFailureHandler);
                    customizer.permitAll();
                })
                .exceptionHandling(customizer -> {
                    customizer.authenticationEntryPoint(this::unauthorizedHandler);
                })
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    private void unauthorizedHandler(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final AuthenticationException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void loginSuccessHandler(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final Authentication authentication) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("X-Auth-Token", authentication.getDetails().toString());
    }

    private void loginFailureHandler(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final AuthenticationException exception) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

}
