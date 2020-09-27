package com.slaand.site.mapper;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper = new UserMapperImpl();

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserEntity expectedEntity;
    private UserDto actualDto;

    @BeforeEach
    public void setUpFields() {
        actualDto = UserDto.builder()
                .name("name")
                .email("email@mail.com")
                .password("password")
                .build();

        expectedEntity = UserEntity.builder()
                .name("name")
                .email("email@mail.com")
                .password("encodedPassword")
                .role(UserRole.USER)
                .build();
    }

    @Test
    void userDtoToUserEntity_isNullUser() {
        UserEntity testEntity = userMapper.userDtoToUserEntity(null, passwordEncoder);
        assertNull(testEntity);
    }

    @Test
    void userDtoToUserEntity_isNullEncoder() {
        assertThrows(NullPointerException.class, () ->
                userMapper.userDtoToUserEntity(actualDto, null));
    }

    @Test
    void userDtoToUserEntity_isNotNull() {

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        UserEntity testEntity = userMapper.userDtoToUserEntity(actualDto, passwordEncoder);
        assertThat(testEntity).isEqualToIgnoringGivenFields(expectedEntity,
                "orders");
    }
}