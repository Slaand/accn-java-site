package com.slaand.site.service.user;

import com.slaand.site.mapper.UserMapper;
import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.patterns.observer.EmailInformationReceived;
import com.slaand.site.patterns.observer.EmailNotificationService;
import com.slaand.site.patterns.observer.EmailPrinterService;
import com.slaand.site.repository.UserRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.ControllerUtils;
import com.slaand.site.util.bootstrap.Alert;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Qualifier("emailService")
    private EmailNotificationService emailNotificationService;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @SneakyThrows
    public String registerAccount(UserDto accountDto,
                                  BindingResult bindingResult,
                                  Model model) {

        boolean isConfirmEmpty = isEmpty(accountDto.getPassword2());

        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "/user/register";
        }

        if(!StringUtils.equals(accountDto.getPassword(), accountDto.getPassword2())) {
            log.debug("Passwords for user " +accountDto.getEmail()+ " are not equal!");
            BootstrapUtils.setAlertModel(model, Alert.WARNING, "Passwords are not equal");
        }

        if (accountExists(accountDto.getEmail())) {
            log.debug("Passwords for user " +accountDto.getEmail()+ " are not equal!");
            BootstrapUtils.setAlertModel(model, Alert.WARNING, "Account exists!");
        }

        UserEntity user = UserMapper.INSTANCE.userDtoToUserEntity(accountDto, passwordEncoder);
        userRepository.save(user);

        return "redirect:/user/login";
    }

    private boolean accountExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
