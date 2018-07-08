package br.com.leonardoferreira.poc.batch;

import br.com.leonardoferreira.poc.domain.Item;
import br.com.leonardoferreira.poc.service.ItemService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class BatchImportWriter implements ItemWriter<Item> {

    @Autowired
    private ItemService itemService;

    @Override
    public void write(List<? extends Item> items) throws Exception {
        items.forEach(itemService::save);
    }
}
