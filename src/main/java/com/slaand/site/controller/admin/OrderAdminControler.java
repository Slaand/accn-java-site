package com.slaand.site.controller.admin;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.service.admin.OrderAdminService;
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
public class OrderAdminControler {

    private OrderAdminService orderAdminService;

    private static final String ORDER_PAGE_PATH = "/admin/review";
    private static final String ORDER_PAGE_REDIRECT_PATH = "redirect:/admin/review";

    public OrderAdminControler(final OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @GetMapping({"", "/", "/order"})
    public String listOrders(Model model) {
        final List<OrderEntity> orders = orderAdminService.retrieveLastTenOrderList();
        model.addAttribute("orders", Objects.requireNonNullElse(orders, Collections.emptyList()));
        model.addAttribute("toEdit", null);
        return ORDER_PAGE_PATH;
    }

    @GetMapping("/order/{id}/delete")
    public String deleteReview(@RequestParam Long id, Model model) {
        boolean isValid = orderAdminService.deleteOrder(id, model);
        if (!isValid) {
            log.debug("Error while processing delete review. Order id: {} and Model: {}", id, model);
        }
        return ORDER_PAGE_REDIRECT_PATH;
    }

    @GetMapping("/order/{id}/edit")
    public String editReview(@RequestParam Long id, Model model) {
        Optional<OrderEntity> order = orderAdminService.retrieveSelectedOrder(id);
        final List<OrderEntity> orders = orderAdminService.retrieveLastTenOrderList();
        model.addAttribute("orders", Objects.requireNonNullElse(orders, Collections.emptyList()));
        model.addAttribute("toEdit", order.orElse(null));
        return ORDER_PAGE_PATH;
    }

    @PostMapping("/order/{id}/edit")
    public String confirmOrderEdit(@Valid OrderDto toEdit, @RequestParam Long id, Model model) {
        boolean isValid = orderAdminService.updateOrder(toEdit, id, model);
        if (!isValid) {
            log.debug("Error while processing delete review. Order DTO: {}, order Id: {} and Model: {}", toEdit, id, model);
        }
        return ORDER_PAGE_REDIRECT_PATH;
    }
}