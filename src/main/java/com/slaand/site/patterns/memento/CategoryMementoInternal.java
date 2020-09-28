package com.slaand.site.patterns.memento;

import com.slaand.site.model.entity.ImageCategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryMementoInternal implements CategoryMemento {

    private Long id;
    private String name;
    private Boolean isHidden;
    private ImageCategoryEntity image;

}
