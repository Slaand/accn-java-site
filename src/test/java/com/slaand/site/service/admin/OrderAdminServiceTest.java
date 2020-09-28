package com.slaand.site.service.admin;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.patterns.state.NewOrder;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderAdminServiceTest {

    @InjectMocks
    private OrderAdminService orderAdminService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<OrderEntity> entityArgumentCaptor;

    private Model mockModel = new ExtendedModelMap();

    private OrderEntity orderEntity;
    private OrderEntity orderEntityTwo;
    private OrderDto orderDto;
    private ItemEntity itemEntity;

    @BeforeEach
    public void setUp() {
        orderEntity = OrderEntity.builder()
                .id(1L)
                .address("orderName")
                .build();

        orderEntityTwo = OrderEntity.builder()
                .id(2L)
                .address("orderName")
                .build();

        orderDto = OrderDto.builder()
                .id(1L)
                .address("editedName")
                .build();

        itemEntity = ItemEntity.builder()
                .id(1L)
                .name("categoryName")
                .build();
    }

    @Test
    void executeOrders_idIsNull() {

        when(orderRepository.findTop10ByOrderByIdDesc())
                .thenReturn(Collections.singletonList(orderEntity));

        String returnUrl = orderAdminService.executeOrders(null, mockModel);

        var orderDto = (OrderDto) mockModel.getAttribute("orderDto");
        assertEquals("/admin/orders", returnUrl);
        assertNotNull(orderDto);
        assertNull(orderDto.getId());
    }

    @Test
    void executeOrders_idIsNotNull() {

        UserEntity userEntity = UserEntity.builder()
                .id(123L)
                .name("nameWow")
                .build();

        ItemEntity itemEntity = ItemEntity.builder()
                .id(123L)
                .name("nameWow")
                .build();

        orderEntityTwo.setUserId(userEntity);
        orderEntityTwo.setItemId(itemEntity);
        orderEntityTwo.setStatus(new NewOrder());

        when(orderRepository.findTop10ByOrderByIdDesc())
                .thenReturn(Arrays.asList(orderEntity, orderEntityTwo));

        when(orderRepository.findById(2L))
                .thenReturn(Optional.ofNullable(orderEntityTwo));

        when(itemRepository.findAllByCategoryId(any()))
                .thenReturn(Collections.singletonList(itemEntity));

        String returnUrl = orderAdminService.executeOrders(2L, mockModel);

        var orderDto = (OrderDto) mockModel.getAttribute("orderDto");
        var orders = (List<OrderEntity>) mockModel.getAttribute("orders");
        var items = (List<ItemEntity>) mockModel.getAttribute("items");
        assertEquals("/admin/orders", returnUrl);
        assertNotNull(orderDto);
        assertNotNull(orders);
        assertNotNull(items);
        assertEquals(2, orders.size());
        assertEquals("orderName", orderDto.getAddress());
        assertEquals("nameWow", items.get(0).getName());
    }

    @Test
    void deleteOrder_noSelectedOrder() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = orderAdminService.deleteOrder(1L, mockModel);
        assertEquals("redirect:/admin/orders", returnUrl);
    }

    @Test
    void deleteOrder_success() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.ofNullable(orderEntity));

        String returnUrl = orderAdminService.deleteOrder(1L, mockModel);
        assertEquals("redirect:/admin/orders", returnUrl);
        verify(orderRepository).delete(entityArgumentCaptor.capture());
    }

    @Test
    void updateOrder_noSelectedOrder() throws IOException {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        String returnUrl = orderAdminService.updateOrder(orderDto, mockModel);
        assertEquals("redirect:/admin/orders", returnUrl);
    }


    @Test
    void updateOrder_success() throws IOException {

        UserEntity userEntity = UserEntity.builder()
                .id(321L)
                .name("userName")
                .build();

        when(orderRepository.findById(1L))
                .thenReturn(Optional.ofNullable(orderEntity));

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemEntity));

        when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(userEntity));

        String returnUrl = orderAdminService.updateOrder(orderDto, mockModel);
        assertEquals("redirect:/admin/orders", returnUrl);
        verify(orderRepository).save(entityArgumentCaptor.capture());
        OrderEntity value = entityArgumentCaptor.getValue();
        assertEquals("editedName", value.getAddress());
    }
}