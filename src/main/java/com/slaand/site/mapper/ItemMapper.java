package com.slaand.site.mapper;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.service.admin.CategoryAdminService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    public static final ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    public abstract ItemEntity itemDtoToItemEntity(ItemDto item, @Context CategoryRepository categoryRepository);

    @InheritConfiguration
    public abstract void itemDtoIntoEntity(ItemDto item, @MappingTarget ItemEntity itemEntity, @Context CategoryRepository categoryRepository);

    @Mapping(target = "categoryId", ignore = true)
    public abstract ItemDto itemEntityToItemDto(ItemEntity item);

    @AfterMapping
    public void mapToEntity(ItemDto item, @MappingTarget ItemEntity itemEntity, @Context CategoryRepository categoryRepository) {
        CategoryEntity entity = categoryRepository.findById(item.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
        itemEntity.setCategoryId(entity);
    }

    @AfterMapping
    public void mapToDto(ItemEntity itemEntity, @MappingTarget ItemDto itemDto) {
        itemDto.setCategoryId(itemEntity.getCategoryId().getId());
    }
    
}
