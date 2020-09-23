package com.slaand.site.service.user;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ProfileService {

    private UserRepository userRepository;

    public ProfileService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity searchUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }

    public String executeProfile(final Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            UserEntity entity = searchUserByEmail(userDetails.getUsername());
            model.addAttribute("user", entity);
            model.addAttribute("orders", entity.getOrders());
            return "/user/profile";
        } else {
            return "redirect:/";
        }
    }

}
