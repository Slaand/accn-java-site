package com.slaand.site.controller;

import com.slaand.site.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/item/{id}")
    public String loadItemById(@RequestParam Long id, Model model) {
        if(id == null) {
            log.debug("Error while loading category. Category id: {} and Model: {}", id, model);
        }
        model.addAttribute("item", itemService.retrieveItemById(id));
        return "index"; //view
    }

    @GetMapping("/item/{id}/buy")
    public String buyItemById(@RequestParam Long id) {
        if(id == null) {
            log.debug("Error while loading category. Category id: {}", id);
        }
        return "redirect:/order/" +id;
    }
}
