package com.slaand.site.model.response;

import com.slaand.site.model.entity.ImageItemEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndexCategoriesDto {

    private String name;
    private ImageItemEntity image;

}
