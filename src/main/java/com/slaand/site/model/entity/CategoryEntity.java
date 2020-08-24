package com.slaand.site.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "tbl_category")
public class CategoryEntity {

    @Id
    private Long id;

    private String name;

    @Column(name = "is_hidden")
    private Boolean isHidden;

    @Column(name = "is_parent")
    private Boolean isParent;

    private Long childOf;

}
