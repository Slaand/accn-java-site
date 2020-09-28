package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.patterns.factory.ElementType;
import com.slaand.site.patterns.factory.WebElementFactory;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private WebElementFactory<ItemEntity> webElementFactory;

    @InjectMocks
    public CategoryService categoryService = new CategoryService(categoryRepository, itemRepository, webElementFactory);

    private Model mockModel = new ExtendedModelMap();

    @Test
    void executeOrder_success() {

        CategoryEntity categoryEntity =  CategoryEntity.builder()
                .id(2L)
                .name("categoryName")
                .build();

        ItemEntity itemEntity = ItemEntity.builder()
                .id(2L)
                .name("categoryName")
                .build();

        when(categoryRepository.findById(any()))
                .thenReturn(Optional.ofNullable(categoryEntity));

        when(itemRepository.findAllByCategoryId(any()))
                .thenReturn(Collections.singletonList(itemEntity));

        String returnUrl = categoryService.executeOrder(2L, mockModel);
        assertEquals("category", returnUrl);
    }
}