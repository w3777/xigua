package com.xigua.rabbitmq;

/**
 * @ClassName RabbitMQConfig
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/27 11:20
 */
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "myQueue";
    private static final String EXCHANGE_NAME = "myExchange";
    private static final String ROUTING_KEY = "myRoutingKey";
    private static final String DEAD_LETTER_EXCHANGE = "myDeadLetterExchange";
    private static final String DEAD_LETTER_QUEUE = "myDeadLetterQueue";
    private static final String DEAD_LETTER_ROUTING_KEY = "myDeadLetterRoutingKey";

    @Bean
    public Queue myQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .withArgument("x-message-ttl", 5000) // 设置消息 TTL 为 5000 毫秒（5秒）
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(myExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }
}

