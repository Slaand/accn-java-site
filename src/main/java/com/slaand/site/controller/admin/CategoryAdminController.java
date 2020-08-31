package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.service.admin.CategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryAdminController {

    private CategoryAdminService categoryAdminService;

    private static final String CATEGORY_PAGE_PATH = "/admin/categories";
    private static final String CATEGORY_PAGE_REDIRECT_PATH = "redirect:/admin/categories";

    public CategoryAdminController(final CategoryAdminService categoryAdminService) {
        this.categoryAdminService = categoryAdminService;
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        final List<CategoryEntity> categories = categoryAdminService.retrieveLastTwelveCategoryList();
        model.addAttribute("categories", Objects.requireNonNullElse(categories, Collections.emptyList()));
        model.addAttribute("toEdit", null);
        return CATEGORY_PAGE_PATH;
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteReview(@RequestParam Long id, Model model) {
        boolean isValid = categoryAdminService.deleteCategory(id, model);
        if (!isValid) {
            log.debug("Error while processing delete category. Category id: {} and Model: {}", id, model);
        }
        return CATEGORY_PAGE_REDIRECT_PATH;
    }

    @GetMapping("/categories/{id}/edit")
    public String editReview(@RequestParam Long id, Model model) {
        Optional<CategoryEntity> category = categoryAdminService.retrieveSelectedCategory(id);
        final List<CategoryEntity> categories = categoryAdminService.retrieveLastTwelveCategoryList();
        model.addAttribute("categories", Objects.requireNonNullElse(categories, Collections.emptyList()));
        model.addAttribute("toEdit", category.orElse(null));
        return CATEGORY_PAGE_PATH;
    }

    @PostMapping("/categories/create")
    public String createCategory(@Valid CategoryDto toEdit, Model model) {
        boolean isValid = categoryAdminService.createCategory(toEdit, model);
        if (!isValid) {
            log.debug("Error while processing delete category. Category DTO: {} and Model: {}", toEdit, model);
        }
        return CATEGORY_PAGE_REDIRECT_PATH;
    }

    @PostMapping("/categories/{id}/edit")
    public String confirmCategoryEdit(@Valid CategoryDto toEdit, @RequestParam Long id, Model model) {
        boolean isValid = categoryAdminService.updateCategory(toEdit, id, model);
        if (!isValid) {
            log.debug("Error while processing delete category. Category DTO: {}, categories Id: {} and Model: {}", toEdit, id, model);
        }
        return CATEGORY_PAGE_REDIRECT_PATH;
    }
}
