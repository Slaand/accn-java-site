package com.slaand.site.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    private Long warranty;

    private String description;

//    @Column(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;

    @OneToMany(
            mappedBy = "itemId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ImageItemEntity> image = new ArrayList<>();

    public void addImage(ImageItemEntity imageItemEntity) {
        image.add(imageItemEntity);
        imageItemEntity.setItemId(this);
    }

    public void removeImage(ImageItemEntity imageItemEntity) {
        image.remove(imageItemEntity);
        imageItemEntity.setItemId(null);
    }

}
