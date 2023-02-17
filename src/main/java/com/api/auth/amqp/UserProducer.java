package com.api.auth.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.amqp.core.Queue;

public class UserProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("user")
    private Queue queue;

    public void send(UserTransfer userTransfer) {
        this.template.convertAndSend(this.queue.getName(), userTransfer);
    }
}
