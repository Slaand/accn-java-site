package com.slaand.site.controller.user;

import com.slaand.site.model.dto.UserDto;
import com.slaand.site.model.entity.UserEntity;
import com.slaand.site.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/user")
public class RegisterController {

    private UserService userService;

    public RegisterController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/"; //view
        } else {
            model.addAttribute("user", UserEntity.builder().build());
            return "/user/register"; //view
        }
    }

    @PostMapping("/register")
    public String addUser(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        return userService.registerAccount(userDto, bindingResult, model);
    }
}
