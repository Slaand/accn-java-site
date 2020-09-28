package com.slaand.site.controller.user;

import com.slaand.site.service.user.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        return profileService.executeProfile(model);
    }

}
