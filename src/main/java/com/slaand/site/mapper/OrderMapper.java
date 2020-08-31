package com.slaand.site.mapper;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

@Mapper
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemId", ignore = true) // TODO repo logic to this place?
    @Mapping(target = "address", source = "order.address")
    public abstract OrderEntity orderDtoToOrderEntity(OrderDto order, UserEntity user, ItemEntity item);

    @AfterMapping
    public void calledWithSourceAndTarget(OrderDto order, UserEntity user, ItemEntity item, @MappingTarget OrderEntity target) {
        target.setCreated(ZonedDateTime.now());
        target.setUpdated(ZonedDateTime.now());
        target.setUserId(user);
        target.setItemId(item);
        target.setStatus(OrderStatus.NEW);
    }

}
