package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    public CategoryService(final CategoryRepository categoryRepository, final ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public List<ItemEntity> getItemsByCategoryId(Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            return itemRepository.findAllByCategoryId(id);
        } else {
            return Collections.emptyList();
        }
    }

}
