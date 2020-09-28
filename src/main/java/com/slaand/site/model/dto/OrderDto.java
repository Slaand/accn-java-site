package com.slaand.site.model.dto;

import com.slaand.site.model.enumerated.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {

    private Long id;
    private Long userId;
    private Long itemId;
    private String address;
    private OrderStatus status;

}
