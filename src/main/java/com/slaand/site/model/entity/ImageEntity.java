package com.slaand.site.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_image")
public class ImageEntity {

    @Id
    private Long id;

    private Long item_id;

    private String base64;

}
