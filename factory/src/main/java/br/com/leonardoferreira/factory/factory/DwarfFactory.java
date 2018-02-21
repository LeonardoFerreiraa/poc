package br.com.leonardoferreira.factory.factory;

import br.com.leonardoferreira.factory.model.Dwarf;
import br.com.leonardoferreira.factory.service.DwarfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by lferreira on 2/20/18
 */
@Component
public class DwarfFactory {

    private EnumMap<Dwarf, DwarfService> map;

    @Autowired
    public DwarfFactory(List<DwarfService> dwarves) {
        this.map = new EnumMap<>(Dwarf.class);
        dwarves.forEach(d -> map.put(d.getType(), d));
    }

    public DwarfService of(Dwarf dwarf) {
        return map.get(dwarf);
    }
}
