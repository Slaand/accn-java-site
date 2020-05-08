package com.slaand.site.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_images")
public class ImageEntity {

    @Id
    private Long id;

}
