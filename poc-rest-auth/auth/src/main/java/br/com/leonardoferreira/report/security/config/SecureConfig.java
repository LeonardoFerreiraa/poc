package br.com.leonardoferreira.report.security.config;

import br.com.leonardoferreira.report.security.filter.SecureFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecureConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private SecureFilter secureFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(secureFilter)
            .addPathPatterns("/**");
    }
}
