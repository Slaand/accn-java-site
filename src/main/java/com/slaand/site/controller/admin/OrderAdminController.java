package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.patterns.observer.EmailInformation;
import com.slaand.site.service.admin.OrderAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class OrderAdminController {

    private OrderAdminService orderAdminService;

    public OrderAdminController(final OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @GetMapping({"", "/orders", "/orders/{id}/edit"})
    public String listOrders(@PathVariable(required = false) Long id, Model model) {
        return orderAdminService.executeOrders(id, model);
    }

    @GetMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id, Model model) {
        return orderAdminService.deleteOrder(id, model);
    }

    @PostMapping("/orders/edit")
    public String confirmOrderEdit(@Valid OrderDto toEdit,
                                   Model model) {
        return orderAdminService.updateOrder(toEdit, model);
    }

    @PostMapping("/orders/email")
    public String sendEmailToUser(@Valid EmailInformation email,
                                  Model model) {
        return orderAdminService.sendEmailToSubscribers(email, model);
    }
}