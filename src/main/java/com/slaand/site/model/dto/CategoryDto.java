package com.slaand.site.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {

    @NotNull
    @NotBlank
    private String name;
    private Boolean isHidden;
    private Boolean isParent;
    private Long childOf;

}
