package com.slaand.site.model.dto;

import lombok.Data;

@Data
public class OrderDto {

    private Long id;
    private Long userId;
    private Long itemId;
    private String address;
    private String status;

}
