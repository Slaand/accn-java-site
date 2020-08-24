package com.slaand.site.service.user;

import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProfileService {

    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public ProfileService(final UserRepository userRepository, final OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public UserEntity searchUserByEmail(final String email) {
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        return entity.orElseThrow(NoSuchElementException::new);
    }

    public List<OrderEntity> searchOrderList(final Long id) {
        return orderRepository.findAllByUserId(id);
    }

}
