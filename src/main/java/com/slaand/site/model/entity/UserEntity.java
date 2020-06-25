package com.slaand.site.model.entity;

import com.slaand.site.model.enumerated.UserRole;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String mobile;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String password;

}
