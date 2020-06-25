package com.slaand.site.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;
    private String surname;
    private String mobile;
    private String email;
    private String password;
    private String password2;

}
