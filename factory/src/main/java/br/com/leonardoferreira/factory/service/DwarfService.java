package br.com.leonardoferreira.factory.service;

import br.com.leonardoferreira.factory.model.Dwarf;

/**
 * Created by lferreira on 2/20/18
 */
public interface DwarfService {

    Dwarf getType();

    Long getExp();

    Long getHp();
}
