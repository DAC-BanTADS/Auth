package com.api.auth.amqp;

import com.api.auth.dtos.UserDto;
import com.api.auth.models.UserModel;
import com.api.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Component("UserHelper")
public class UserHelper {
    final UserService userService;

    public UserHelper(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<String> saveUser(@Valid UserDto userDto){
        if(userService.existsByEmail(userDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: O e-mail já está sendo utilizado!");
        }

        var userModel = new UserModel();

        userModel.setNome(userDto.getNome());
        userModel.setEmail(userDto.getEmail());
        String encodedPassword = Base64.getEncoder().encodeToString(userDto.getSenha().getBytes());
        userModel.setSenha(encodedPassword);
        userModel.setCargo(userDto.getCargo());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel).getId());
    }

    public ResponseEntity<String> updateUserEmail(String email, UserDto userDto){
        Optional<UserModel> userModelOptional = userService.findByEmail(email);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        userModelOptional.get().setEmail(userDto.getEmail());
        userService.save(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso.");
    }

    public ResponseEntity<String> deleteUser(String email){
        Optional<UserModel> userModelOptional = userService.findByEmail(email);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }
}