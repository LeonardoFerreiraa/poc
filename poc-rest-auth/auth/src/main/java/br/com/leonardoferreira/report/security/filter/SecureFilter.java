package br.com.leonardoferreira.report.security.filter;


import br.com.leonardoferreira.report.security.annotation.SecureAccess;
import br.com.leonardoferreira.report.security.domain.Account;
import br.com.leonardoferreira.report.security.domain.CurrentUser;
import br.com.leonardoferreira.report.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecureFilter implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        SecureAccess secureAccess = hm.getMethod().getAnnotation(SecureAccess.class);
        if (secureAccess == null) {
            secureAccess = hm.getBean().getClass().getAnnotation(SecureAccess.class);
        }

        if (secureAccess != null) {
            String authorization = request.getHeader("Authorization");
            Account account = authService.isValidToken(authorization);
            if (account == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }

            currentUser.setAccount(account);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
