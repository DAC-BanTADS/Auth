package com.api.auth.amqp;

import com.api.auth.dtos.UserDto;
import com.api.auth.models.UserModel;
import com.api.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class UserHelper {
    final UserService userService;

    public UserHelper(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Object> saveUser(@Valid UserDto userDto){
        if(userService.existsByEmail(userDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: O e-mail já está sendo utilizado!");
        }

        var userModel = new UserModel();

        userModel.setNome(userDto.getNome());
        userModel.setEmail(userDto.getEmail());
        userModel.setSenha(new BCryptPasswordEncoder(Integer.parseInt(userDto.getSenha())));
        userModel.setCargo(userDto.getCargo());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }
}