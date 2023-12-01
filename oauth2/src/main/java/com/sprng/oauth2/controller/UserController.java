package com.sprng.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth2/user")
public class UserController {
    @GetMapping("/authorize")
    public  String getUserAuthorizeForm(){
        return "UserLoginDataFormHTML";
    }
}
