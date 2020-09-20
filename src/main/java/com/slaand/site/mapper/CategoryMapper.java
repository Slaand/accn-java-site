package com.slaand.site.mapper;

import com.slaand.site.model.dto.CategoryDto;
import com.slaand.site.model.entity.CategoryEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract CategoryEntity categoryDtoToCategoryEntity(CategoryDto category);

    public abstract CategoryDto categoryEntityToCategoryDto(CategoryEntity category);

    @InheritConfiguration
    public abstract void categoryDtoIntoEntity(CategoryDto categoryDto, @MappingTarget CategoryEntity categoryEntity);

}
