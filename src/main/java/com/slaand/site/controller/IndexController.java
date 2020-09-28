package com.slaand.site.controller;

import com.slaand.site.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping({"", "/", "/index"})
    public String mainPage(Model model) {
        return indexService.executeIndex(model);
    }
}