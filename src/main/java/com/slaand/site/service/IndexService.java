package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class IndexService {

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    public IndexService(final CategoryRepository categoryRepository, final ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public String executeIndex(Model model) {
        model.addAttribute("categories", getLastCategories());
        model.addAttribute("items", getLastItems());
        return "index";
    }

    public List<CategoryEntity> getLastCategories() {
        List<CategoryEntity> categories = categoryRepository.findTop12ByOrderByIdDesc();
        return Objects.requireNonNullElse(categories, Collections.emptyList());
    }

    public List<ItemEntity> getLastItems() {
        List<ItemEntity> items = itemRepository.findTop6ByOrderByIdDesc();
        return Objects.requireNonNullElse(items, Collections.emptyList());
    }
}