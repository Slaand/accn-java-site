package com.slaand.site.service.admin;

import com.slaand.site.mapper.CategoryMapper;
import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.bootstrap.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryAdminService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<CategoryEntity> retrieveSelectedCategory(final Long id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryEntity> retrieveLastTwelveCategoryList() {
        return categoryRepository.findTop12ByOrderByIdDesc();
    }

    public boolean createCategory(final CategoryDto toEdit, final Model model) {
        CategoryEntity categoryEntity = CategoryMapper.INSTANCE.categoryDtoToCategoryEntity(toEdit);
        categoryRepository.save(categoryEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Категория создана успешно!");
        return true;
    }

    public boolean deleteCategory(final Long id, final Model model) {
        Optional<CategoryEntity> categoryEntity = retrieveSelectedCategory(id);
        if (categoryEntity.isPresent()) {
            categoryRepository.delete(categoryEntity.get());
            BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Раздел был успешно удалён!");
        } else {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
            return false;
        }
        return true;
    }

    public boolean updateCategory(final CategoryDto toEdit, final Long id, final Model model) {
        CategoryEntity categoryEntity = retrieveSelectedCategory(id).orElse(null);
        if(toEdit == null || categoryEntity == null) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return false;
        }
        //categoryEntity.setItemId(toEdit.getItemId()); todo mapper?
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return true;
    }
}