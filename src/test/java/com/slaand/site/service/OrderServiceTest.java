package com.slaand.site.service;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.OrderRepository;
import com.slaand.site.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<OrderEntity> entityArgumentCaptor;

    private Model mockModel = new ExtendedModelMap();

    private OrderDto orderDto;
    private ItemEntity itemEntity;
    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        orderDto = OrderDto.builder()
                .id(1L)
                .address("editedName")
                .build();

        itemEntity = ItemEntity.builder()
                .id(1L)
                .name("categoryName")
                .build();

        userEntity = UserEntity.builder()
                .id(123L)
                .name("userName")
                .role(UserRole.USER)
                .build();
    }

    @Test
    void executeOrder_idIsNull() {

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemEntity));

        String returnUrl = orderService.executeOrder(1L, userEntity, mockModel);

        var itemDto = (ItemEntity) mockModel.getAttribute("item");
        assertEquals("order", returnUrl);
        assertNotNull(itemDto);
        assertEquals(1, itemDto.getId());
    }

    @Test
    void createOrder_success() throws IOException {

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemEntity));

        when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(userEntity));

        String returnUrl = orderService.createOrder(orderDto, mockModel);

        assertEquals("redirect:/index", returnUrl);
        verify(orderRepository).save(entityArgumentCaptor.capture());
        OrderEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getAddress());
    }
}