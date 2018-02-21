package br.com.leonardoferreira.factory.resolver;

import br.com.leonardoferreira.factory.model.Dwarf;
import br.com.leonardoferreira.factory.service.DwarfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lferreira on 2/20/18
 */
@Component
public class DwarfServiceResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private List<DwarfService> dwarves;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(DwarfService.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Dwarf dwarf = Dwarf.valueOf(getPathVariable(webRequest));
        return dwarves.stream().filter(d -> dwarf.equals(d.getType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Dwarf not found"));
    }

    @SuppressWarnings("unchecked")
    private String getPathVariable(NativeWebRequest webRequest) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String> variables = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        return variables.get("dwarf");
    }
}
