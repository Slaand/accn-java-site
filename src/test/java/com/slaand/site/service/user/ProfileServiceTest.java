package com.slaand.site.service.user;

import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.model.enumerated.UserRole;
import com.slaand.site.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private Model mockModel = new ExtendedModelMap();

    @Test
    @WithMockUser(username = "anonymous", roles = { "" })
    void searchUserByEmail_notLoggedIn() {

        AnonymousAuthenticationToken token = new AnonymousAuthenticationToken("123", new Object(),
                AuthorityUtils.createAuthorityList("authority"));

        when(securityContext.getAuthentication()).thenReturn(token);
        SecurityContextHolder.setContext(securityContext);

        String resultUrl = profileService.executeProfile(mockModel);
        assertEquals("redirect:/", resultUrl);
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    void searchUserByEmail_isLoggedIn() {

        UserEntity userEntity = UserEntity.builder()
                .id(12L)
                .role(UserRole.USER)
                .email("email@mail.com")
                .build();

        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user").password("password").roles("USER").build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.ofNullable(userEntity));

        String resultUrl = profileService.executeProfile(mockModel);
        assertEquals("/user/profile", resultUrl);

    }
}