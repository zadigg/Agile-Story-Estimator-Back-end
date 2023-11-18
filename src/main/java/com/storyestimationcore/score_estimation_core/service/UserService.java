package com.storyestimationcore.score_estimation_core.service;

import com.storyestimationcore.score_estimation_core.domain.User;
import com.storyestimationcore.score_estimation_core.exception.CustomException;
import com.storyestimationcore.score_estimation_core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        if (user == null) {
            throw new CustomException("Invalid user payload");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new CustomException("Username is empty");
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new CustomException("User role is empty");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomException("User already exists");
        }

        return userRepository.save(user);
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("User not found"); // CustomException is an example, you can create your own exception class.
        }
        return user;
    }


    public User updateUser(String userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new CustomException("User not found");
        }
        existingUser.get().setUsername(updatedUser.getUsername());
        existingUser.get().setRole(updatedUser.getRole());
        return userRepository.save(existingUser.get());
    }



    public boolean deleteUser(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new CustomException("User not found");
        }
        userRepository.deleteByUsername(username);
        return true;
    }
}
