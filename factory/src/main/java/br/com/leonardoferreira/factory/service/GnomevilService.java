package br.com.leonardoferreira.factory.service;

import br.com.leonardoferreira.factory.model.Dwarf;
import org.springframework.stereotype.Service;

/**
 * Created by lferreira on 2/20/18
 * https://www.tibiawiki.com.br/wiki/Gnomevil
 */
@Service
public class GnomevilService implements DwarfService {

    @Override
    public Dwarf getType() {
        return Dwarf.GNOMEVIL;
    }

    @Override
    public Long getExp() {
        return 45000L;
    }

    @Override
    public Long getHp() {
        return 250000L;
    }

}
