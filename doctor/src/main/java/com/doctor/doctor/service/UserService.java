package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.entity.User;
import com.doctor.doctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin(origins = "http://127.0.0.1:3001") // Spécifiez l'origine autorisée


public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor with @Autowired is not needed due to @RequiredArgsConstructor
    // However, if you need a default constructor, you must initialize userRepository.
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll(); // Method to retrieve all users
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
    }

    public User updateUser(Long id, User userDetails) {
        User user = findById(id); // This method will throw an exception if the user is not found
        user.setEmail(userDetails.getEmail());

        // Do not encode the password if the password has not changed
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Update other fields as necessary
        return userRepository.save(user);
    }
}
