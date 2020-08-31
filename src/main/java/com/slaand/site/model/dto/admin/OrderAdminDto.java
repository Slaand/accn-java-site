package com.slaand.site.model.dto.admin;

import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.UserEntity;
import lombok.Data;

@Data
public class OrderAdminDto {

    private UserEntity user;
    private ItemEntity item;

}
