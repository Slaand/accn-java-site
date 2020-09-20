package com.slaand.site.mapper;

import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.service.admin.CategoryAdminService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    public static final ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    public abstract ItemEntity itemDtoToItemEntity(ItemDto item, @Context CategoryAdminService categoryService);

    @InheritConfiguration
    public abstract void itemDtoIntoEntity(ItemDto item, @MappingTarget ItemEntity itemEntity, @Context CategoryAdminService categoryService);

    @Mapping(target = "categoryId", ignore = true)
    public abstract ItemDto itemEntityToItemDto(ItemEntity item);

    @AfterMapping
    public void mapToEntity(ItemDto item, @MappingTarget ItemEntity itemEntity, @Context CategoryAdminService categoryService) {
        CategoryEntity entity = categoryService.retrieveSelectedCategory(item.getCategoryId());
        itemEntity.setCategoryId(entity);
    }

    @AfterMapping
    public void mapToDto(ItemEntity itemEntity, @MappingTarget ItemDto itemDto) {
        itemDto.setCategoryId(itemEntity.getCategoryId().getId());
    }
    
}
