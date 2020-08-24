package com.slaand.site.controller;

import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{id}")
    public String orderItem(@RequestParam Long id, @AuthenticationPrincipal UserEntity user, Model model) {
        if(id == null || user == null) {
            log.debug("Error while loading order details. Category id: {}, User: {} and Model: {}", id, user, model);
        }
        model.addAttribute("item", orderService.loadItemById(id));
        return "index"; //view
    }

    // todo make order
}
