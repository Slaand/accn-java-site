package com.slaand.site.service;

import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Object retrieveItemById(final Long id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        return item.orElseThrow(NoSuchElementException::new);
    }
}
