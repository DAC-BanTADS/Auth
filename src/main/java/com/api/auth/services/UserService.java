package com.api.auth.services;

import com.api.auth.models.UserModel;
import com.api.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserService")
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findByEmailAndSenha(String email, String senha) {
        return userRepository.findByEmailAndSenha(email, senha);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}