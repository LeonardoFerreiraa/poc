package br.com.leonardoferreira.factory.controller;

import br.com.leonardoferreira.factory.factory.DwarfFactory;
import br.com.leonardoferreira.factory.model.Dwarf;
import br.com.leonardoferreira.factory.service.DwarfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lferreira on 2/20/18
 */
@RestController
@RequestMapping("/dwarves/{dwarf}")
public class DwarfController {

    @Autowired
    private DwarfFactory dwarfFactory;

    @GetMapping("/exp")
    public Long getExp(@PathVariable Dwarf dwarf) {
        DwarfService dwarfService = dwarfFactory.of(dwarf);
        return dwarfService.getExp();
    }

    @GetMapping("/hp")
    public Long getHp(DwarfService dwarfService) {
        return dwarfService.getHp();
    }

}
