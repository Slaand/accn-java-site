package com.slaand.site.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private Double price;
    private Long warranty;
    private String description;

    private Long categoryId;

}
