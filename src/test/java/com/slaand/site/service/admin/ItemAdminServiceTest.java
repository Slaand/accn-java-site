package com.slaand.site.service.admin;

import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemAdminServiceTest {

    @InjectMocks
    private ItemAdminService itemAdminService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Captor
    private ArgumentCaptor<ItemEntity> entityArgumentCaptor;

    private Model mockModel = new ExtendedModelMap();

    private ItemEntity itemEntity;
    private ItemEntity itemEntityTwo;
    private ItemDto itemDto;
    private CategoryEntity categoryEntity;

    @BeforeEach
    public void setUp() {
        itemEntity = ItemEntity.builder()
                .id(1L)
                .name("itemName")
                .build();

        itemEntityTwo = ItemEntity.builder()
                .id(2L)
                .name("itemName")
                .build();

        itemDto = ItemDto.builder()
                .id(1L)
                .name("editedName")
                .build();

        categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("categoryName")
                .build();
    }

    @Test
    void executeItems_idIsNull() {

        when(itemRepository.findTop12ByOrderByIdDesc())
                .thenReturn(Collections.singletonList(itemEntity));

        when(categoryRepository.findAll())
                .thenReturn(Collections.singletonList(categoryEntity));

        String returnUrl = itemAdminService.executeItems(null, mockModel);

        var itemDto = (ItemDto) mockModel.getAttribute("itemDto");
        var items = (List<ItemEntity>) mockModel.getAttribute("items");
        var categories = (List<CategoryEntity>) mockModel.getAttribute("categories");
        assertEquals("/admin/items", returnUrl);
        assertNotNull(itemDto);
        assertNotNull(items);
        assertNotNull(categories);
        assertEquals(1, items.size());
        assertEquals("itemName", items.get(0).getName());
        assertEquals("categoryName", categories.get(0).getName());
        assertNull(itemDto.getId());
    }

    @Test
    void executeItems_idIsNotNull() {

        CategoryEntity entity = CategoryEntity.builder()
                .id(123L)
                .name("nameWow")
                .build();

        itemEntityTwo.setCategoryId(entity);

        when(itemRepository.findTop12ByOrderByIdDesc())
                .thenReturn(Arrays.asList(itemEntity, itemEntityTwo));

        when(itemRepository.findById(2L))
                .thenReturn(Optional.ofNullable(itemEntityTwo));

        when(categoryRepository.findAll())
                .thenReturn(Collections.singletonList(categoryEntity));

        String returnUrl = itemAdminService.executeItems(2L, mockModel);

        var itemDto = (ItemDto) mockModel.getAttribute("itemDto");
        var items = (List<ItemEntity>) mockModel.getAttribute("items");
        var categories = (List<CategoryEntity>) mockModel.getAttribute("categories");
        assertEquals("/admin/items", returnUrl);
        assertNotNull(itemDto);
        assertNotNull(items);
        assertNotNull(categories);
        assertEquals(2, items.size());
        assertEquals("itemName", itemDto.getName());
        assertEquals("categoryName", categories.get(0).getName());
    }

    @Test
    void createItem_success() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());

        when(categoryRepository.findById(any()))
                .thenReturn(Optional.ofNullable(categoryEntity));

        String returnUrl = itemAdminService.createItem(itemDto, file, mockModel);

        assertEquals("redirect:/admin/items", returnUrl);
        verify(itemRepository).save(entityArgumentCaptor.capture());
        ItemEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getName());
    }

    @Test
    void deleteItem_noSelectedItem() {

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = itemAdminService.deleteItem(1L, mockModel);
        assertEquals("redirect:/admin/items", returnUrl);
    }

    @Test
    void deleteItem_success() {

        when(itemRepository.findById(1L))
                .thenReturn(Optional.ofNullable(itemEntity));

        String returnUrl = itemAdminService.deleteItem(1L, mockModel);
        assertEquals("redirect:/admin/items", returnUrl);
        verify(itemRepository).delete(entityArgumentCaptor.capture());
    }

    @Test
    void updateItem_noSelectedItem() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = itemAdminService.updateItem(itemDto, file, mockModel);
        assertEquals("redirect:/admin/items", returnUrl);
    }


    @Test
    void updateItem_success() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());
        when(itemRepository.findById(1L))
                .thenReturn(Optional.ofNullable(itemEntity));
        when(categoryRepository.findById(any()))
                .thenReturn(Optional.ofNullable(categoryEntity));

        String returnUrl = itemAdminService.updateItem(itemDto, file, mockModel);

        assertEquals("redirect:/admin/items", returnUrl);
        verify(itemRepository).save(entityArgumentCaptor.capture());
        ItemEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getName());
    }
}