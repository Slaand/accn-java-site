package com.slaand.site.controller;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{id}")
    public String orderItem(@PathVariable Long id, Model model) {
        return orderService.executeOrder(id, model);
    }

    @PostMapping("/order/confirm")
    public String orderItem(@Valid OrderDto toEdit, Model model) {
        return orderService.createOrder(toEdit, model);
    }
}
