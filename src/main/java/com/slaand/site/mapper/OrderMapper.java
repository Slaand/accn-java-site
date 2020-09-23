package com.slaand.site.mapper;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.repository.UserRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Mapper
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "itemId", ignore = true)
    public abstract OrderEntity orderDtoToOrderEntity(OrderDto order,
                                                      @Context ItemRepository itemRepository,
                                                      @Context UserRepository userRepository);

    @InheritConfiguration
    public abstract void orderDtoIntoEntity(OrderDto order,
                                            @MappingTarget OrderEntity orderEntity,
                                            @Context ItemRepository itemRepository,
                                            @Context UserRepository userRepository);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "itemId", ignore = true)
    public abstract OrderDto orderEntityToOrderDto(OrderEntity order);

    @AfterMapping
    public void mapToEntity(OrderDto order,
                            @MappingTarget OrderEntity orderEntity,
                            @Context ItemRepository itemRepository,
                            @Context UserRepository userRepository) {

        ItemEntity itemEntity = itemRepository.findById(order.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Not found!"));

        UserEntity userEntity = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Not found!"));

        orderEntity.setUserId(userEntity);
        orderEntity.setItemId(itemEntity);
        if(orderEntity.getStatus() == null) {
            orderEntity.setStatus(OrderStatus.NEW);
        }
        if(orderEntity.getCreated() == null) {
            orderEntity.setCreated(ZonedDateTime.now());
        }
        orderEntity.setUpdated(ZonedDateTime.now());
    }

    @AfterMapping
    public void mapToDto(OrderEntity orderEntity, @MappingTarget OrderDto orderDto) {
        orderDto.setItemId(orderEntity.getItemId().getId());
        orderDto.setUserId(orderEntity.getUserId().getId());
    }

}
