package br.com.leonardoferreira.poc.restspringsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final ObjectMapper objectMapper;

    private final Algorithm algorithm = Algorithm.HMAC256("xablau");

    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public TokenService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encode(final Account userDetails) {
        try {
            return JWT.create()
                    .withSubject(objectMapper.writeValueAsString(userDetails))
                    .sign(algorithm);
        } catch (final Exception e) {
            throw new RuntimeException("failed to create token", e);
        }
    }

    public Account decode(final String token) {
        try {
            final DecodedJWT decodedJWT = verifier.verify(token);
            return objectMapper.readValue(decodedJWT.getSubject(), Account.class);
        } catch (final Exception e) {
            throw new RuntimeException("failed to read token", e);
        }
    }

}
