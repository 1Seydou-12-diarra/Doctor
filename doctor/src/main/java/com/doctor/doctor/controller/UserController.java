package com.doctor.doctor.controller;

import com.doctor.doctor.config.JwtUtil;
import com.doctor.doctor.entity.LoginResponse;
import com.doctor.doctor.entity.User;
import com.doctor.doctor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3001")

@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        System.out.println("Tentative de connexion pour l'utilisateur : " + user.getEmail());
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser == null) {
            System.out.println("Utilisateur introuvable: " + user.getEmail());
            return new ResponseEntity<>(new LoginResponse(null, "Invalid email or password", null), HttpStatus.UNAUTHORIZED);
        }

        boolean isPasswordMatch = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
        if (!isPasswordMatch) {
            System.out.println("Mot de passe incorrect pour: " + user.getEmail());
            return new ResponseEntity<>(new LoginResponse(null, "Invalid email or password", null), HttpStatus.UNAUTHORIZED);
        }

        List<String> roles = existingUser.getRoles();
        String token = jwtUtil.generateToken(existingUser.getEmail(), roles, existingUser.getId().toString(), existingUser.getFirstName(), existingUser.getLastName());
        System.out.println("Connexion réussie pour: " + user.getEmail());

        return new ResponseEntity<>(new LoginResponse(token, "Connexion réussie", existingUser.getUsername()), HttpStatus.OK);
    }
}
