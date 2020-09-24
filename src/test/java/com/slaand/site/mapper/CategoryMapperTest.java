package com.slaand.site.mapper;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ImageCategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CategoryMapperTest {

    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    private CategoryEntity expectedEntity;
    private CategoryDto expectedDto;

    @BeforeEach
    public void setUpFields() {
        expectedDto = CategoryDto.builder()
                .id(123L)
                .isHidden(true)
                .name("categoryName")
                .build();

        ImageCategoryEntity entity = ImageCategoryEntity.builder().build();

        expectedEntity = CategoryEntity.builder()
                .id(321L)
                .isHidden(false)
                .image(entity)
                .name("categoryName")
                .build();
    }

    @Test
    void categoryDtoToCategoryEntity_isNull() {
        CategoryEntity testEntity = categoryMapper.categoryDtoToCategoryEntity(null);
        assertNull(testEntity);
    }

    @Test
    void categoryDtoToCategoryEntity_isNotNull() {
        CategoryDto actualDto = CategoryDto.builder()
                .id(321L)
                .isHidden(false)
                .name("categoryName")
                .build();

        CategoryEntity testEntity = categoryMapper.categoryDtoToCategoryEntity(actualDto);
        assertThat(testEntity).isEqualToIgnoringGivenFields(expectedEntity, "image");
    }

    @Test
    void categoryEntityToCategoryDto_isNull() {
        CategoryDto testDto = categoryMapper.categoryEntityToCategoryDto(null);
        assertNull(testDto);
    }

    @Test
    void categoryEntityToCategoryDto_isNotNull() {
        CategoryEntity actualEntity = CategoryEntity.builder()
                .id(123L)
                .isHidden(true)
                .name("categoryName")
                .build();

        CategoryDto testDto = categoryMapper.categoryEntityToCategoryDto(actualEntity);
        assertThat(testDto).isEqualToComparingFieldByField(expectedDto);
    }

    @Test
    void categoryDtoIntoEntity_isNullDto() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(12345L)
                .build();
        categoryMapper.categoryDtoIntoEntity(null, categoryEntity);
        assertNotNull(categoryEntity.getId());
    }

    @Test
    void categoryDtoIntoEntity_isNullEntity() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(54321L)
                .build();
        assertThrows(NullPointerException.class, () ->
                categoryMapper.categoryDtoIntoEntity(categoryDto, null));
    }

    @Test
    void categoryDtoIntoEntity_isNotNull() {
        CategoryDto testDto = CategoryDto.builder()
                .id(321L)
                .isHidden(false)
                .name("categoryName")
                .build();

        CategoryEntity testEntity = CategoryEntity.builder()
                .id(321L)
                .isHidden(true)
                .name("oldName")
                .build();

        categoryMapper.categoryDtoIntoEntity(testDto, testEntity);
        assertThat(testEntity).isEqualToIgnoringGivenFields(expectedEntity, "image");
    }

}