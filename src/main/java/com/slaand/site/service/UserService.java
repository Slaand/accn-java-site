package com.slaand.site.service;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import com.slaand.site.repository.UsersRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    public UserService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @SneakyThrows
    public UserEntity registerNewUserAccount(UserDto accountDto) {

        if(!StringUtils.equals(accountDto.getPassword(), accountDto.getPassword2())) {
            log.debug("Passwords for user " +accountDto.getEmail()+ " are not equal!");
            throw new Exception("Passwords are not equal");
        }

        if (accountExists(accountDto.getEmail())) {
            log.debug("Passwords for user " +accountDto.getEmail()+ " are not equal!");
            throw new Exception("Account exists!");
        }

        UserEntity user = UserEntity.builder()
                .name(accountDto.getName())
                .surname(accountDto.getSurname())
                .mobile(accountDto.getMobile())
                .email(accountDto.getEmail())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .role(UserRole.USER)
                .build();

        return usersRepository.save(user);
    }

    private boolean accountExists(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

}
