package com.slaand.site.service.user;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserService userService = new UserService(userRepository, passwordEncoder);

    private Model mockModel = new ExtendedModelMap();

    @Test
    void registerAccount() {

        UserDto userDto = UserDto.builder()
                .name("name")
                .email("mail@mail.com")
                .password("myPass")
                .password2("myPass")
                .build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("1234567890");

        String returnUrl = userService.registerAccount(userDto, bindingResult, mockModel);
        assertEquals("redirect:/user/login", returnUrl);
    }

    @Test
    void registerAccount_hasErrors() {

        UserDto userDto = UserDto.builder()
                .name("name")
                .email("mail@mail.com")
                .password("myPass")
                .password2("myPass")
                .build();

        when(bindingResult.hasErrors()).thenReturn(true);

        String returnUrl = userService.registerAccount(userDto, bindingResult, mockModel);
        assertEquals("/user/register", returnUrl);
    }

    @Test
    void registerAccount_passwordNotEqual() {

        UserDto userDto = UserDto.builder()
                .name("name")
                .email("mail@mail.com")
                .password("myPass")
                .password2("myPass2")
                .build();

        when(bindingResult.hasErrors()).thenReturn(false);

        String returnUrl = userService.registerAccount(userDto, bindingResult, mockModel);
        assertEquals("/user/register", returnUrl);
    }

    @Test
    void registerAccount_accountExists() {

        UserDto userDto = UserDto.builder()
                .name("name")
                .email("mail@mail.com")
                .password("myPass")
                .password2("myPass")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("name")
                .email("mail@mail.com")
                .build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userEntity));

        String returnUrl = userService.registerAccount(userDto, bindingResult, mockModel);
        assertEquals("/user/register", returnUrl);
    }

}