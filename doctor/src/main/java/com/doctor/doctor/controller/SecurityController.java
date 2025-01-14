package com.doctor.doctor.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3001")

@RequestMapping("/demo/")
public class SecurityController {

    @GetMapping("/protected")
    public String helloSecure() {
        return "Salut, vous êtes en privé";
    }

    @GetMapping("/public")
    public String helloPublic() {
        return "Salut, vous êtes connecté en tant que public";
    }

    @GetMapping("/user")
    public String helloUser() {
        return "Salut, vous êtes connecté en tant qu'utilisateur";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Salut, vous êtes connecté en tant qu'Admin";
    }
}