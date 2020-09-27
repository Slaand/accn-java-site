package com.slaand.site.mapper;

import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.service.admin.CategoryAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemMapperTest {

    private ItemMapper itemMapper = new ItemMapperImpl();

    @Mock
    private CategoryRepository categoryRepository;

    private ItemEntity expectedEntity;
    private ItemDto expectedDto;

    @BeforeEach
    public void setUpFields() {
        expectedDto = ItemDto.builder()
                .id(123L)
                .name("itemName")
                .build();

        expectedEntity = ItemEntity.builder()
                .id(321L)
                .name("itemName")
                .build();
    }

    @Test
    void itemDtoToItemEntity_isNullItem() {
        ItemEntity testEntity = itemMapper.itemDtoToItemEntity(null, categoryRepository);
        assertNull(testEntity);
    }

    @Test
    void itemDtoToItemEntity_isNullService() {
        ItemDto itemDto = ItemDto.builder()
                .id(321L)
                .build();
        assertThrows(NullPointerException.class, () ->
                itemMapper.itemDtoToItemEntity(itemDto, null));
    }

    @Test
    void itemDtoToItemEntity_isNotNull() {
        ItemDto actualDto = ItemDto.builder()
                .id(321L)
                .name("itemName")
                .categoryId(1L)
                .build();

        CategoryEntity itemEntity = CategoryEntity.builder()
                .id(321L)
                .name("itemName")
                .isHidden(true)
                .build();

        when(categoryRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemEntity));

        ItemEntity testEntity = itemMapper.itemDtoToItemEntity(actualDto, categoryRepository);
        assertThat(testEntity).isEqualToIgnoringGivenFields(expectedEntity, "image", "categoryId");
    }

    @Test
    void itemDtoIntoEntity_isNullDto() {
        ItemEntity itemEntity = ItemEntity.builder()
                .id(12345L)
                .build();
        itemMapper.itemDtoIntoEntity(null, itemEntity, categoryRepository);
        assertNotNull(itemEntity.getId());
    }

    @Test
    void itemDtoIntoEntity_isNullEntity() {
        ItemDto itemDto = ItemDto.builder()
                .id(54321L)
                .build();
        assertThrows(NullPointerException.class, () ->
                itemMapper.itemDtoIntoEntity(itemDto, null, categoryRepository));
    }

    @Test
    void itemDtoIntoEntity_isNotNull() {
        ItemDto testDto = ItemDto.builder()
                .id(321L)
                .name("itemName")
                .build();

        ItemEntity testEntity = ItemEntity.builder()
                .id(321L)
                .name("oldName")
                .build();

        CategoryEntity itemEntity = CategoryEntity.builder()
                .id(321L)
                .name("itemName")
                .isHidden(true)
                .build();

        when(categoryRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemEntity));

        itemMapper.itemDtoIntoEntity(testDto, testEntity, categoryRepository);
        assertThat(testEntity).isEqualToIgnoringGivenFields(expectedEntity, "image", "categoryId");
    }

    @Test
    void itemEntityToItemDto_isNull() {
        ItemDto testDto = itemMapper.itemEntityToItemDto(null);
        assertNull(testDto);
    }

    @Test
    void itemEntityToItemDto_isNotNull() {
        ItemEntity actualEntity = ItemEntity.builder()
                .id(123L)
                .name("itemName")
                .categoryId(CategoryEntity.builder().id(678L).build())
                .build();

        expectedDto.setCategoryId(678L);

        ItemDto testDto = itemMapper.itemEntityToItemDto(actualEntity);
        assertThat(testDto).isEqualToComparingFieldByField(expectedDto);
    }

}