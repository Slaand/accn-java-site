package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.patterns.factory.ElementType;
import com.slaand.site.patterns.factory.WebElementFactory;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;
    private WebElementFactory<ItemEntity> webElementFactory;

    public CategoryService(final CategoryRepository categoryRepository,
                           final ItemRepository itemRepository,
                           final WebElementFactory<ItemEntity> webElementFactory) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.webElementFactory = webElementFactory;
    }

    public List<ItemEntity> getItemsByCategoryId(Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        List<ItemEntity> items = category.isPresent() ?
                itemRepository.findAllByCategoryId(category.get()) :
                Collections.emptyList();

        items.addAll(webElementFactory.fillWithDummyElements(ElementType.ITEM, 40, items.size()));
        return items;
    }

    public String executeOrder(final Long id, final Model model) {
        model.addAttribute("itemPage", getItemsByCategoryId(id));
        return "category";
    }
}
