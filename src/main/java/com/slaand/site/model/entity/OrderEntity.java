package com.slaand.site.model.entity;

import com.slaand.site.model.enumerated.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@Builder
@Entity
@Table(name = "tbl_order")
public class OrderEntity {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "item_id")
    private Long itemId;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String address;

}
