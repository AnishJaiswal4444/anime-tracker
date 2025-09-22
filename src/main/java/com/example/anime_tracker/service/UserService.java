package com.example.anime_tracker.service;

import com.example.anime_tracker.dto.UserDTO;
import com.example.anime_tracker.model.User;
import com.example.anime_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAnimeListService userAnimeListService;

    @Transactional
    public UserDTO createUser(String username, String email, String password) {
        // Optional: Add checks for existing username/email here

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Later: hash password!
        User saved = userRepository.save(user);
        userAnimeListService.createDefaultListsForUser(saved);
        return toDTO(saved);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
