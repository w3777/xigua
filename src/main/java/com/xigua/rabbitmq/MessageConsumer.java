package com.xigua.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @ClassName MessageConsumer
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/27 13:03
 */
@Component
public class MessageConsumer {

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception{
        System.out.println("Received message: " + message);
//        channel.basicNack(tag,false,true);
//        channel.basicAck(tag,true);
        // Process the received message
    }

    @RabbitListener(queues = "myDeadLetterQueue")
    public void receiveDeadLetterMessage(String message) {
        System.out.println("Received dead letter message: " + message);
        // Process the received dead letter message
    }
}
