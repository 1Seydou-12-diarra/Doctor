package com.doctor.doctor.service;

import com.doctor.doctor.entity.User;
import com.doctor.doctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "http://127.0.0.1:3001") // Spécifiez l'origine autorisée

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Accéder aux utilisateurs

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username); // Récupérer l'utilisateur par e-mail
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Récupérer les rôles de l'utilisateur et les convertir en GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role)); // Ajoutez directement le nom du rôle
            });
        }

        // Créer et retourner un objet UserDetails avec les rôles
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}