package br.com.leonardoferreira.poc.multitenancy.config.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginAuthenticationProvider loginAuthenticationProvider;

    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    public WebSecurityConfig(LoginAuthenticationProvider loginAuthenticationProvider, TokenAuthenticationProvider tokenAuthenticationProvider) {
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                    .successHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setHeader("X-Auth-Token", authentication.getDetails().toString());
                    })
                    .failureHandler((request, response, exception) -> {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                    })
                    .permitAll()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    })
                .and()
                .anonymous()
                    .disable()
                .httpBasic()
                    .disable()
                .csrf()
                    .disable()
                .cors()
                    .disable()
                .addFilterBefore(new AuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/accounts");
    }

}