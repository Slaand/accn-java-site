package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.patterns.factory.WebElementFactory;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WebElementFactory webElementFactory;

    @InjectMocks
    private IndexService indexService = new IndexService(categoryRepository, itemRepository, webElementFactory);

    private Model mockModel = new ExtendedModelMap();

    @Test
    void executeIndex_success() {

        ItemEntity itemEntity = ItemEntity.builder()
                .id(1L)
                .name("itemName")
                .build();

        CategoryEntity categoryEntity =  CategoryEntity.builder()
                .id(2L)
                .name("categoryName")
                .build();

        when(categoryRepository.findTop12ByOrderByIdDesc())
                .thenReturn(Collections.singletonList(categoryEntity));

        when(itemRepository.findTop6ByOrderByIdDesc())
                .thenReturn(Collections.singletonList(itemEntity));

        String returnUrl = indexService.executeIndex(mockModel);
        assertEquals("index", returnUrl);

    }


}