package br.com.leonardoferreira.poc.multitenancy.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final Signer signer;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public LoginAuthenticationProvider(Signer signer, UserDetailsService userDetailsService, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.signer = signer;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails ud = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if (!passwordEncoder.matches((CharSequence) authentication.getCredentials(), ud.getPassword())) {
            throw new BadCredentialsException("Password don't match");
        }

        Jwt jwt = JwtHelper.encode(objectMapper.writeValueAsString(ud), signer);

        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(ud, ud.getPassword(), ud.getAuthorities());
        user.setDetails(jwt.getEncoded());

        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}