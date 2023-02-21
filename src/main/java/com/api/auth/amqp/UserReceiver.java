package com.api.auth.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Objects;
import java.util.UUID;

@RabbitListener(queues = "user")
public class UserReceiver {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private UserProducer userProducer;
    @Autowired
    private UserHelper userHelper;

    @RabbitHandler
    public UserTransfer receive(@Payload UserTransfer userTransfer) {
        if (userTransfer.getAction().equals("save-user")) {
            if (Objects.isNull(userTransfer.getUserDto().getEmail())) {
                userTransfer.setAction("failed-user");
                userTransfer.setMessage(("Nenhum dado de User foi passado."));
                return userTransfer;
            }

            ResponseEntity<String> response = userHelper.saveUser(userTransfer.getUserDto());

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                userTransfer.setAction("success-user");
                userTransfer.setMessage(response.getBody());
                return userTransfer;
            }

            userTransfer.setAction("failed-user");
            userTransfer.setMessage(Objects.requireNonNull(response.getBody()));
            return userTransfer;
        }

        if (userTransfer.getAction().equals("update-user")) {
            ResponseEntity<String> response = userHelper.updateUserEmail(userTransfer.getMessage(), userTransfer.getUserDto());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                userTransfer.setAction("success-user");
                userTransfer.setMessage(response.getBody());
                return userTransfer;
            }

            userTransfer.setAction("failed-user");
            userTransfer.setMessage(Objects.requireNonNull(response.getBody()));
            return userTransfer;
        }

        if (userTransfer.getAction().equals("delete-user")) {
            ResponseEntity<String> response = userHelper.deleteUser(userTransfer.getMessage());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                userTransfer.setAction("success-user");
                userTransfer.setMessage(response.getBody());
                return userTransfer;
            }

            userTransfer.setAction("failed-user");
            userTransfer.setMessage(response.getBody());
            return userTransfer;
        }

        userTransfer.setAction("failed-user");
        userTransfer.setMessage("Ação informada não existe.");
        return userTransfer;
    }
}