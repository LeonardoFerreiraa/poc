package br.com.leonardoferreira.factory.service;

import br.com.leonardoferreira.factory.model.Dwarf;
import org.springframework.stereotype.Service;

/**
 * Created by lferreira on 2/20/18
 * https://www.tibiawiki.com.br/wiki/Lloyd
 */
@Service
public class LloydService implements DwarfService {
    @Override
    public Dwarf getType() {
        return Dwarf.LLOYD;
    }

    @Override
    public Long getExp() {
        return 66000L;
    }

    @Override
    public Long getHp() {
        return 64000L;
    }
}
