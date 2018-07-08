package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.domain.Item;
import br.com.leonardoferreira.poc.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void save(Item item) {
        log.info("Method=save, item={}", item);
        itemRepository.save(item);
    }
}
