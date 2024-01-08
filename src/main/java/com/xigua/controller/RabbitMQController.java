package com.xigua.controller;

import com.xigua.rabbitmq.MessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RabbitMQController
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/27 13:32
 */
@AllArgsConstructor
@RestController
@RequestMapping("rabbitmq")
public class RabbitMQController {
    private MessageProducer messageProducer;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("test01")
    public void test01(){
        String message = "123456";
//        messageProducer.sendMessage("123456");
        rabbitTemplate.convertAndSend("myExchange", "myRoutingKey", message, msg -> {
            msg.getMessageProperties().setExpiration("5000");
            return msg;
        });

    }
}
