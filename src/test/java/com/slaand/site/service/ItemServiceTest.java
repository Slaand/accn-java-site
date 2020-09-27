package com.slaand.site.service;

import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private Model mockModel = new ExtendedModelMap();

    @Test
    void executeItem_success() {

        ItemEntity itemEntity = ItemEntity.builder()
                .id(1L)
                .name("itemName")
                .build();

        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(itemEntity));

        itemService.executeItem(1L, mockModel);

    }
}