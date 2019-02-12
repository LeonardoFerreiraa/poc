package br.com.leonardoferreira.poc.multitenancy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;

@Configuration
public class SignerConfig {

    @Bean
    public MacSigner macSigner() {
        return new MacSigner("super-secret");
    }

    @Bean
    public Signer signer(MacSigner macSigner) {
        return macSigner;
    }

    @Bean
    public SignatureVerifier verifier(MacSigner macSigner) {
        return macSigner;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
