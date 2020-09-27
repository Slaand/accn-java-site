package com.slaand.site.service.admin;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.repository.CategoryRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryAdminServiceTest {

    @InjectMocks
    private CategoryAdminService categoryAdminService;

    @Mock
    private CategoryRepository categoryRepository;

    @Captor
    private ArgumentCaptor<CategoryEntity> entityArgumentCaptor;

    private Model mockModel = new ExtendedModelMap();

    private CategoryEntity categoryEntity;
    private CategoryEntity categoryEntityTwo;
    private CategoryDto categoryDto;

    @BeforeEach
    public void setUp() {
        categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("categoryName")
                .build();

        categoryEntityTwo = CategoryEntity.builder()
                .id(2L)
                .name("categoryName")
                .build();

        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("editedName")
                .build();
    }

    @Test
    void executeCategories_idIsNull() {

        when(categoryRepository.findTop12ByOrderByIdDesc())
                .thenReturn(Collections.singletonList(categoryEntity));

        String returnUrl = categoryAdminService.executeCategories(null, mockModel);

        var categoryDto = (CategoryDto) mockModel.getAttribute("categoryDto");
        var categories = (List<CategoryEntity>) mockModel.getAttribute("categories");
        assertEquals("/admin/categories", returnUrl);
        assertNotNull(categoryDto);
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertNull(categoryDto.getId());
    }

    @Test
    void executeCategories_idIsNotNull() {

        when(categoryRepository.findTop12ByOrderByIdDesc())
                .thenReturn(Arrays.asList(categoryEntity, categoryEntityTwo));

        when(categoryRepository.findById(2L))
                .thenReturn(Optional.ofNullable(categoryEntityTwo));

        String returnUrl = categoryAdminService.executeCategories(2L, mockModel);

        var categoryDto = (CategoryDto) mockModel.getAttribute("categoryDto");
        var categories = (List<CategoryEntity>) mockModel.getAttribute("categories");
        assertEquals("/admin/categories", returnUrl);
        assertNotNull(categoryDto);
        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals("categoryName", categoryDto.getName());
    }

    @Test
    void createCategory() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());

        String returnUrl = categoryAdminService.createCategory(categoryDto, file, mockModel);

        assertEquals("redirect:/admin/categories", returnUrl);
        verify(categoryRepository).save(entityArgumentCaptor.capture());
        CategoryEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getName());
    }

    @Test
    void deleteCategory_noSelectedCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = categoryAdminService.deleteCategory(1L, mockModel);
        assertEquals("redirect:/admin/categories", returnUrl);
    }

    @Test
    void deleteCategory_success() {

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.ofNullable(categoryEntity));

        String returnUrl = categoryAdminService.deleteCategory(1L, mockModel);
        assertEquals("redirect:/admin/categories", returnUrl);
        verify(categoryRepository).delete(entityArgumentCaptor.capture());
    }

    @Test
    void updateCategory_noSelectedCategory() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = categoryAdminService.updateCategory(categoryDto, file, mockModel);
        assertEquals("redirect:/admin/categories", returnUrl);
    }


    @Test
    void updateCategory_success() throws IOException {

        MultipartFile file = new MockMultipartFile("name.jpg", InputStream.nullInputStream());
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.ofNullable(categoryEntity));
        String returnUrl = categoryAdminService.updateCategory(categoryDto, file, mockModel);
        assertEquals("redirect:/admin/categories", returnUrl);
        verify(categoryRepository).save(entityArgumentCaptor.capture());
        CategoryEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getName());
    }
}