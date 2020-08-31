package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.service.admin.ItemAdminService;
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
public class ItemAdminController {

    private ItemAdminService itemAdminService;
    
    private static final String ITEM_PAGE_PATH = "/admin/items";
    private static final String ITEM_PAGE_REDIRECT_PATH = "redirect:/admin/items";

    public ItemAdminController(final ItemAdminService itemAdminService) {
        this.itemAdminService = itemAdminService;
    }
    
    @GetMapping("/items")
    public String listItems(Model model) {
        final List<ItemEntity> items = itemAdminService.retrieveLastTenItemList();
        model.addAttribute("items", Objects.requireNonNullElse(items, Collections.emptyList()));
        model.addAttribute("toEdit", null);
        return ITEM_PAGE_PATH;
    }

    @GetMapping("/items/{id}/delete")
    public String deleteItem(@RequestParam Long id, Model model) {
        boolean isValid = itemAdminService.deleteItem(id, model);
        if (!isValid) {
            log.debug("Error while processing delete item. Item id: {} and Model: {}", id, model);
        }
        return ITEM_PAGE_REDIRECT_PATH;
    }

    @GetMapping("/items/{id}/edit")
    public String editItem(@RequestParam Long id, Model model) {
        Optional<ItemEntity> item = itemAdminService.retrieveSelectedItem(id);
        final List<ItemEntity> items = itemAdminService.retrieveLastTenItemList();
        model.addAttribute("items", Objects.requireNonNullElse(items, Collections.emptyList()));
        model.addAttribute("toEdit", item.orElse(null));
        return ITEM_PAGE_PATH;
    }

    @PostMapping("/items/{id}/create")
    public String createItem(@Valid ItemDto toEdit, @RequestParam Long id, Model model) {
        boolean isValid = itemAdminService.createItem(toEdit, model);
        if (!isValid) {
            log.debug("Error while processing delete item. Item DTO: {}, item Id: {} and Model: {}", toEdit, id, model);
        }
        return ITEM_PAGE_REDIRECT_PATH;
    }

    @PostMapping("/items/{id}/edit")
    public String confirmItemEdit(@Valid ItemDto toEdit, @RequestParam Long id, Model model) {
        boolean isValid = itemAdminService.updateItem(toEdit, id, model);
        if (!isValid) {
            log.debug("Error while processing delete item. Item DTO: {}, item Id: {} and Model: {}", toEdit, id, model);
        }
        return ITEM_PAGE_REDIRECT_PATH;
    }


}
