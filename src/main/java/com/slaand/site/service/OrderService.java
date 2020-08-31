package com.slaand.site.service;

import com.slaand.site.mapper.OrderMapper;
import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean createOrder(final OrderDto order, final UserEntity user) {
        if (user == null || order == null) {
            return false;
        }
        Optional<ItemEntity> itemEntity = itemRepository.findById(order.getItemId());
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDtoToOrderEntity(order, user, itemEntity.get());
        orderRepository.save(orderEntity);
        return true;
    }

    @SneakyThrows
    public ItemEntity loadItemById(Long id) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(id);
        return itemEntity.orElseThrow(NoSuchAlgorithmException::new);
    }
}
