package com.slaand.site.service;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String executeItem(final Long id, final Model model) {
        model.addAttribute("item", retrieveSelectedItem(id));
        return "item";
    }

    public ItemEntity retrieveSelectedItem(final Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }

}
