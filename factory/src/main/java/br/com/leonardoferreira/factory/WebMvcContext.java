package br.com.leonardoferreira.factory;

import br.com.leonardoferreira.factory.resolver.DwarfServiceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
  
@Configuration
class WebMvcContext implements WebMvcConfigurer {

    @Autowired
    private DwarfServiceResolver dwarfServiceResolver;
  
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(dwarfServiceResolver);
    }
}