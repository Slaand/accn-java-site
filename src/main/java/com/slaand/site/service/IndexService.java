package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.patterns.factory.ElementType;
import com.slaand.site.patterns.factory.WebElementFactory;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class IndexService {

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;
    private WebElementFactory webElementFactory;

    public IndexService(final CategoryRepository categoryRepository,
                        final ItemRepository itemRepository,
                        final WebElementFactory webElementFactory) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.webElementFactory = webElementFactory;
    }

    public String executeIndex(Model model) {
        model.addAttribute("categories", getLastCategories());
        model.addAttribute("items", getLastItems());
        return "index";
    }

    public List<CategoryEntity> getLastCategories() {
        List<CategoryEntity> categories = categoryRepository.findTop12ByOrderByIdDesc();
        categories.addAll(webElementFactory.fillWithDummyElements(ElementType.CATEGORY, 12, categories.size()));
        return categories;
    }

    public List<ItemEntity> getLastItems() {
        List<ItemEntity> items = itemRepository.findTop6ByOrderByIdDesc();
        items.addAll(webElementFactory.fillWithDummyElements(ElementType.ITEM, 6, items.size()));
        return items;
    }
}