package com.slaand.site.controller;

import com.slaand.site.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{id}")
    public String loadCategory(@RequestParam Long id, Model model) {
        if(id == null) {
            log.debug("Error while loading category. Category id: {} and Model: {}", id, model);
        }
        model.addAttribute("category", categoryService.getItemsByCategoryId(id));
        return "index"; //view
    }
}