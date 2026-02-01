package com.mudanca.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden // Oculta este endpoint da listagem do próprio Swagger
public class RedirectApi {

    @GetMapping("/")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }

}