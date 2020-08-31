package com.slaand.site.mapper;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


//@Mapper(
//        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
//        componentModel = "spring",
//        uses = {
//                // My other mappers...
//        })
@Mapper(componentModel="spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserEntity userDtoToUserEntity(UserDto userDto, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    public void fillInformation(UserDto source,
                                @MappingTarget UserEntity target,
                                @Context PasswordEncoder passwordEncoder) {
        target.setPassword(passwordEncoder.encode(source.getPassword()));
        target.setRole(UserRole.USER);
    }

}
