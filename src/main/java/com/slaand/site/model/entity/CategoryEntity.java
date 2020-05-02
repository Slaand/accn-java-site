package com.slaand.site.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category")
public class CategoryEntity {

    @Id
    private Long id;

}
