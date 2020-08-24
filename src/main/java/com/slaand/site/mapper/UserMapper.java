package com.slaand.site.mapper;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserEntity userDtoToUserEntity(UserDto car);

    @AfterMapping
    protected void fillInformation(@MappingTarget UserEntity target, UserDto source) {
        target.setPassword(passwordEncoder.encode(source.getPassword()));
        target.setRole(UserRole.USER);
    }

}
