package com.slaand.site.service.admin;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.mapper.CategoryMapper;
import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ImageCategoryEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.OtherUtils;
import com.slaand.site.util.bootstrap.Alert;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryAdminService {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String CATEGORY_PAGE_PATH = "/admin/categories";
    private static final String CATEGORY_PAGE_REDIRECT_PATH = "redirect:/admin/categories";

    public String executeCategories(final Long id, final Model model) {

        final List<CategoryEntity> categories = retrieveLastTwelveCategoryList();
        model.addAttribute("categories", Objects.requireNonNullElse(categories, Collections.emptyList()));
        if (ObjectUtils.isEmpty(id)) {
            model.addAttribute("categoryDto", new CategoryDto());
        } else {
            CategoryEntity category = retrieveSelectedCategory(id);
            CategoryDto dto = CategoryMapper.INSTANCE.categoryEntityToCategoryDto(category);
            model.addAttribute("categoryDto", dto);
        }
        return CATEGORY_PAGE_PATH;
    }

    public String createCategory(final CategoryDto toEdit, final MultipartFile file, final Model model) {

        CategoryEntity categoryEntity = CategoryMapper.INSTANCE.categoryDtoToCategoryEntity(toEdit);
        categoryEntity.setImage(getBase64FromImageFile(file, categoryEntity));
        categoryRepository.save(categoryEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Категория создана успешно!");
        return CATEGORY_PAGE_REDIRECT_PATH;
    }

    public String deleteCategory(final Long id, final Model model) {

        CategoryEntity categoryEntity = Try.of(() -> retrieveSelectedCategory(id))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
                    return null;
                });

        if (ObjectUtils.isEmpty(categoryEntity)) {
            return CATEGORY_PAGE_REDIRECT_PATH;
        }

        categoryRepository.delete(categoryEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Раздел был успешно удалён!");
        return CATEGORY_PAGE_REDIRECT_PATH;
    }

    public String updateCategory(final CategoryDto toEdit, final MultipartFile file, final Model model) {

        CategoryEntity categoryEntity = Try.of(() -> retrieveSelectedCategory(toEdit.getId()))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
                    return null;
                });

        if(ObjectUtils.isEmpty(categoryEntity)) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return CATEGORY_PAGE_REDIRECT_PATH;
        }

        CategoryMapper.INSTANCE.categoryDtoIntoEntity(toEdit, categoryEntity);
        if(!file.isEmpty()) {
            categoryEntity.setImage(getBase64FromImageFile(file, categoryEntity));
        }

        categoryRepository.save(categoryEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return CATEGORY_PAGE_REDIRECT_PATH;
    }

    @SneakyThrows
    private ImageCategoryEntity getBase64FromImageFile(final MultipartFile file,
                                                       final CategoryEntity categoryEntity) {
        return ImageCategoryEntity.builder()
                .categoryId(categoryEntity)
                .base64(OtherUtils.getBase64FromImageFile(file))
                .build();
    }

    public CategoryEntity retrieveSelectedCategory(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }

    private List<CategoryEntity> retrieveLastTwelveCategoryList() {
        return categoryRepository.findTop12ByOrderByIdDesc();
    }

}