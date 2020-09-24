package com.slaand.site.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
