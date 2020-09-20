package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.service.admin.CategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryAdminController {

    private CategoryAdminService categoryAdminService;

    public CategoryAdminController(final CategoryAdminService categoryAdminService) {
        this.categoryAdminService = categoryAdminService;
    }

    @GetMapping({"/categories", "/categories/{id}/edit"})
    public String listCategories(@PathVariable(required = false) Long id, Model model) {
        return categoryAdminService.executeCategories(id, model);
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteReview(@PathVariable Long id, Model model) {
        return categoryAdminService.deleteCategory(id, model);
    }

    @PostMapping("/categories/create")
    public String createCategory(@Valid CategoryDto toEdit,
                                 @RequestParam("file") MultipartFile file,
                                 Model model) {
        return categoryAdminService.createCategory(toEdit, file, model);
    }

    @PostMapping("/categories/edit")
    public String confirmCategoryEdit(@Valid CategoryDto toEdit,
                                      @RequestParam("file") MultipartFile file,
                                      Model model) {
        return categoryAdminService.updateCategory(toEdit, file, model);
    }
}
