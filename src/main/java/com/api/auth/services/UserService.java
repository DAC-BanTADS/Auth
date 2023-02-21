package com.api.auth.services;

import com.api.auth.models.UserModel;
import com.api.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("UserService")
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserModel> findByEmailAndSenha(String email, String senha) {
        return userRepository.findByEmailAndSenha(email, senha);
    }

    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}