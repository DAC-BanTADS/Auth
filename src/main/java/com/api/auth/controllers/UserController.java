package com.api.auth.controllers;

import com.api.auth.dtos.LoginDto;
import com.api.auth.models.UserModel;
import com.api.auth.services.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> validaAuth(@RequestBody @Valid LoginDto loginDto) {
        BCryptPasswordEncoder password = new BCryptPasswordEncoder(Integer.parseInt(loginDto.getSenha()));
        Optional<UserModel> userModelOptional = userService.findByEmailAndSenha(loginDto.getEmail(), password);

        if (userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro! Usuário e/ou senha incorretos!");
        }
    }
}
