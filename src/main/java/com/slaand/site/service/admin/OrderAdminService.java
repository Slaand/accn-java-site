package com.slaand.site.service.admin;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.mapper.OrderMapper;
import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import com.slaand.site.patterns.observer.EmailInformation;
import com.slaand.site.patterns.observer.EmailNotificationService;
import com.slaand.site.patterns.observer.MessageType;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.repository.UserRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.bootstrap.Alert;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Service
public class OrderAdminService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("emailService")
    private EmailNotificationService emailNotificationService;

    public String executeOrders(final Long id, final Model model) {

        final List<OrderEntity> orders = retrieveLastTwelveOrderList();
        model.addAttribute("orders", Objects.requireNonNullElse(orders, Collections.emptyList()));
        if (ObjectUtils.isEmpty(id)) {
            model.addAttribute("orderDto", new OrderDto());
        } else {
            OrderEntity order = retrieveSelectedOrder(id);
            OrderDto dto = OrderMapper.INSTANCE.orderEntityToOrderDto(order);
            model.addAttribute("orderDto", dto);
            List<OrderStatus> orderStatuses = new ArrayList<>(EnumSet.allOf(OrderStatus.class));
            model.addAttribute("statuses", orderStatuses);
            List<ItemEntity> categoryItems = itemRepository.findAllByCategoryId(order.getItemId().getCategoryId());
            model.addAttribute("items", categoryItems);
        }
        List<MessageType> emailMessageTypes = new ArrayList<>(EnumSet.allOf(MessageType.class));
        model.addAttribute("types", emailMessageTypes);
        model.addAttribute("emailInformation", new EmailInformation());
        return "/admin/orders";
    }

    public String deleteOrder(final Long id, final Model model) {

        OrderEntity orderEntity = Try.of(() -> retrieveSelectedOrder(id))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Заказ не был найден!");
                    return null;
                });

        if (ObjectUtils.isEmpty(orderEntity)) {
            return "redirect:/admin/orders";
        }

        orderRepository.delete(orderEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Заказ был успешно удалён!");
        return "redirect:/admin/orders";
    }

    public String updateOrder(final OrderDto toEdit, final Model model) {

        OrderEntity orderEntity = Try.of(() -> retrieveSelectedOrder(toEdit.getId()))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
                    return null;
                });

        if(ObjectUtils.isEmpty(orderEntity)) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return "redirect:/admin/orders";
        }

        OrderMapper.INSTANCE.orderDtoIntoEntity(toEdit, orderEntity, itemRepository, userRepository);

        orderRepository.save(orderEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return "redirect:/admin/orders";
    }

    public OrderEntity retrieveSelectedOrder(final Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }

    private List<OrderEntity> retrieveLastTwelveOrderList() {
        return orderRepository.findTop10ByOrderByIdDesc();
    }

    public String sendEmailToSubscribers(final EmailInformation email, final Model model) {
        List<UserEntity> subscribed = userRepository.findAllByIsSubscribed(true);
        for (UserEntity user : subscribed) {
            email.setName(user.getName());
            emailNotificationService.publish(email);
        }
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Уведомления отправлены!");
        return "redirect:/admin/orders";
    }
}