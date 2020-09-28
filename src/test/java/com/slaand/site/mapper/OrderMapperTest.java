package com.slaand.site.mapper;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import com.slaand.site.patterns.state.NewOrder;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    private OrderMapper orderMapper = new OrderMapperImpl();

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    private OrderEntity expectedEntity;
    private OrderDto expectedDto;

    @BeforeEach
    public void setUpFields() {
        expectedDto = OrderDto.builder()
                .id(123L)
                .address("orderAddress")
                .status(OrderStatus.DELIVERED)
                .build();

        expectedEntity = OrderEntity.builder()
                .id(321L)
                .address("orderAddress")
                .status(OrderStatus.DELIVERED.get(expectedEntity))
                .build();
    }

    @Test
    void orderDtoToOrderEntity_isNullOrder() {
        OrderEntity testEntity = orderMapper.orderDtoToOrderEntity(null, itemRepository, userRepository);
        assertNull(testEntity);
    }

    @Test
    void orderDtoToOrderEntity_isNullRepository() {
        OrderDto orderDto = OrderDto.builder()
                .id(321L)
                .build();
        assertThrows(NullPointerException.class, () ->
                orderMapper.orderDtoToOrderEntity(orderDto, null, userRepository));
        assertThrows(ResourceNotFoundException.class, () ->
                orderMapper.orderDtoToOrderEntity(orderDto, itemRepository, null));
    }

    @Test
    void orderDtoToOrderEntity_isNotNull() {
        OrderDto actualDto = OrderDto.builder()
                .id(321L)
                .userId(1111L)
                .itemId(2222L)
                .address("orderAddress")
                .status(OrderStatus.DELIVERED)
                .build();

        when(itemRepository.findById(2222L)).thenReturn(Optional.of(ItemEntity.builder().id(2222L).build()));
        when(userRepository.findById(1111L)).thenReturn(Optional.of(UserEntity.builder().id(1111L).build()));

        OrderEntity testEntity = orderMapper.orderDtoToOrderEntity(actualDto, itemRepository, userRepository);
        assertEquals(expectedEntity.getAddress(), testEntity.getAddress());
        assertEquals("NEW", testEntity.getStatus().status);
    }

    @Test
    void orderDtoIntoEntity_isNullDto() {
        orderMapper.orderDtoIntoEntity(null, expectedEntity, itemRepository, userRepository);
        assertNotNull(expectedEntity.getId());
    }

    @Test
    void orderDtoIntoEntity_isNullEntity() {
        assertThrows(NullPointerException.class, () ->
                orderMapper.orderDtoIntoEntity(expectedDto, null, itemRepository, userRepository));
    }

    @Test
    void orderDtoIntoEntity_isNullRepository() {

        assertThrows(NullPointerException.class, () ->
                orderMapper.orderDtoIntoEntity(expectedDto, expectedEntity, null, userRepository));

        assertThrows(ResourceNotFoundException.class, () ->
                orderMapper.orderDtoIntoEntity(expectedDto, expectedEntity, itemRepository, null));
    }

    @Test
    void orderDtoIntoEntity_isNotNull() {
        OrderDto testDto = OrderDto.builder()
                .id(321L)
                .itemId(2222L)
                .userId(1111L)
                .status(OrderStatus.DELIVERED)
                .address("orderAddress")
                .build();

        OrderEntity testEntity = OrderEntity.builder()
                .id(321L)
                .build();

        testEntity.setStatus(new NewOrder());

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(ItemEntity.builder().id(2222L).build()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserEntity.builder().id(1111L).build()));

        orderMapper.orderDtoIntoEntity(testDto, testEntity, itemRepository, userRepository);
        assertEquals(OrderStatus.DELIVERED.name(), testEntity.getStatus().status);
        assertEquals(expectedEntity.getAddress(), testEntity.getAddress());
    }

    @Test
    void orderEntityToOrderDto_isNull() {
        OrderDto testDto = orderMapper.orderEntityToOrderDto(null);
        assertNull(testDto);
    }

    @Test
    void orderEntityToOrderDto_isNotNull() {
        OrderEntity actualEntity = OrderEntity.builder()
                .id(123L)
                .userId(UserEntity.builder().id(678L).build())
                .itemId(ItemEntity.builder().id(543L).build())
                .address("orderAddress")
                .build();

        actualEntity.setStatus(new NewOrder());

        expectedDto.setUserId(678L);
        expectedDto.setItemId(543L);
        expectedDto.setStatus(OrderStatus.NEW);

        OrderDto testDto = orderMapper.orderEntityToOrderDto(actualEntity);
        assertThat(testDto).isEqualToComparingFieldByField(expectedDto);
    }
}