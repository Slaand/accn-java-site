package com.slaand.site.mapper;

import com.slaand.site.model.dto.OrderDto;
import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

@Mapper
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    public abstract OrderEntity orderDtoToOrderEntity(OrderDto car, UserEntity user);

    @AfterMapping
    public void calledWithSourceAndTarget(OrderDto car, UserEntity user, @MappingTarget OrderEntity target) {
        target.setCreated(ZonedDateTime.now());
        target.setUpdated(ZonedDateTime.now());
        target.setUserId(user.getId());
        target.setStatus(OrderStatus.NEW);
    }

}
