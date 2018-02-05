package br.com.leonardoferreira.pocsleuthfeign.controller;

import br.com.leonardoferreira.pocsleuthfeign.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lferreira
 * @since 2/5/18 1:42 PM
 */
@Slf4j
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/myep")
    public String health(@RequestHeader MultiValueMap<String, ?> headers, @RequestParam(required = false, defaultValue = "false") Boolean propagate) {
        log.info("M=health, headers={}", headers);
        return applicationService.health(propagate);
    }

    @GetMapping("/myep/check")
    public String healthCheck(@RequestHeader MultiValueMap<String, ?> headers, @RequestParam(required = false, defaultValue = "false") Boolean propagate) {
        log.info("M=healthCheck, headers={}", headers);
        return applicationService.healthCheck(propagate);
    }
}
