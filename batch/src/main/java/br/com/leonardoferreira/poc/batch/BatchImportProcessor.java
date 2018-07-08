package br.com.leonardoferreira.poc.batch;

import br.com.leonardoferreira.poc.domain.Item;
import br.com.leonardoferreira.poc.mapper.ItemMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class BatchImportProcessor implements ItemProcessor<BatchImportItem, Item> {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item process(BatchImportItem batchImportItem) throws Exception {
        return itemMapper.itemFromBatchImportItem(batchImportItem);
    }
}
