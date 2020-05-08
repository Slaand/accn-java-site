package com.slaand.site.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    private Long id;

}
