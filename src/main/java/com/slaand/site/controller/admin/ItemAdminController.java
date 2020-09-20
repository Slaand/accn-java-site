package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.service.admin.ItemAdminService;
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
public class ItemAdminController {

    private ItemAdminService itemAdminService;

    public ItemAdminController(final ItemAdminService itemAdminService) {
        this.itemAdminService = itemAdminService;
    }

    @GetMapping({"/items", "/items/{id}/edit"})
    public String listItems(@PathVariable(required = false) Long id, Model model) {
        return itemAdminService.executeItems(id, model);
    }

    @GetMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id, Model model) {
        return itemAdminService.deleteItem(id, model);
    }

    @PostMapping("/items/create")
    public String createItem(@Valid ItemDto toEdit,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        return itemAdminService.createItem(toEdit, file, model);
    }

    @PostMapping("/items/edit")
    public String confirmItemEdit(@Valid ItemDto toEdit,
                                  @RequestParam("file") MultipartFile file,
                                  Model model) {
        return itemAdminService.updateItem(toEdit, file, model);
    }

}
