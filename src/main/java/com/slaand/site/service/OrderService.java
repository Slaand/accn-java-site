package com.slaand.site.service;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.mapper.OrderMapper;
import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.repository.UserRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.bootstrap.Alert;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;

    public String executeOrder(final Long id, final UserEntity user, final Model model) {

        final ItemEntity item = retrieveSelectedItem(id);
        model.addAttribute("item", item);
        model.addAttribute("user", ObjectUtils.defaultIfNull(user, new UserEntity()));
        model.addAttribute("order", new OrderDto());
        List<OrderStatus> orderStatuses = new ArrayList<>(EnumSet.allOf(OrderStatus.class));
        model.addAttribute("statuses", orderStatuses);
        return "order";
    }

    public String createOrder(final OrderDto toEdit, final Model model) {

        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDtoToOrderEntity(toEdit, itemRepository, userRepository);
        orderRepository.save(orderEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Категория создана успешно!");
        return "redirect:/index";
    }

    @SneakyThrows
    private ItemEntity retrieveSelectedItem(final Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }
}
