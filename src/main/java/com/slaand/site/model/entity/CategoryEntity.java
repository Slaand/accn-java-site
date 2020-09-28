package com.slaand.site.model.entity;

import com.slaand.site.patterns.factory.WebElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_category")
public class CategoryEntity implements WebElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "is_hidden")
    private Boolean isHidden;

    @OneToOne(
            mappedBy = "categoryId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ImageCategoryEntity image = new ImageCategoryEntity();

    public void setEncodedPicture(String base64) {
        ImageCategoryEntity tempImage = new ImageCategoryEntity();
        tempImage.setBase64(base64);
        tempImage.setCategoryId(this);
        setImage(tempImage);
    }

}
