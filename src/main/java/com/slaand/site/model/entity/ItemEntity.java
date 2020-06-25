package com.slaand.site.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item")
public class ItemEntity {

    @Id
    private Long id;

    private String name;

    private Double price;

    private Long warranty;

    private String description;

    @Column(name = "category_id")
    private Long categoryId;

}
