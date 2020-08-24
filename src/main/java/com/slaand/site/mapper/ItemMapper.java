package com.slaand.site.mapper;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ItemMapper {

    public static final ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    public abstract ItemEntity itemDtoToItemEntity(ItemDto item);

}
