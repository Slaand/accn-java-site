package com.slaand.site.controller;

import com.slaand.site.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/item/{id}")
    public String loadItemById(@PathVariable Long id, Model model) {
        return itemService.executeItem(id, model);
    }
}
