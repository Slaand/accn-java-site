package com.slaand.site.service.admin;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.bootstrap.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class OrderAdminService {

    @Autowired
    private OrderRepository orderRepository;

    // admin panel functions
    public List<OrderEntity> retrieveLastTenOrderList() {
        return orderRepository.findTop10ByOrderByIdDesc();
    }

    public Optional<OrderEntity> retrieveSelectedOrder(Long id) {
        return orderRepository.findById(id);
    }

    public boolean deleteOrder(final Long id, final Model model) {
        Optional<OrderEntity> order = retrieveSelectedOrder(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Заказ был успешно удалён!");
        } else {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Заказ не был найден!");
            return false;
        }
        return true;
    }

    public boolean updateOrder(OrderDto orderDto, final Long id, final Model model) {
        OrderEntity order = retrieveSelectedOrder(id).orElse(null);
        if(orderDto == null || order == null) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return false;
        }
        order.setItemId(orderDto.getItemId());
        order.setAddress(orderDto.getAddress());
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return true;
    }
}