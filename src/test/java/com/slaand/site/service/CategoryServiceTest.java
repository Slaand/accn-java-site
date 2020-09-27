package com.slaand.site.service;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ItemRepository itemRepository;

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

        categoryService.executeOrder(2L, mockModel);
    }
}