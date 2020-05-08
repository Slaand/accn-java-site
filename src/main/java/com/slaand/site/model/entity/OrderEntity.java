package com.slaand.site.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_order")
public class OrderEntity {

    @Id
    private Long id;

}
