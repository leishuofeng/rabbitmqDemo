package com.study.shizhanzhinan.chapters_4;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.shizhanzhinan.util.RabbitmqConnectFatory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 过期时间设置 TTL  time to live rabbitmq 可以对消息和队列设置ttl
 * 过期时间到后，被设置了过期时间的队列会被自动删除，
 * 在RabbitMQ重启之后，持久化的队列的过期时间会被重新计算。（未亲自尝试）
 */
public class RabbitmqProducer4_queue_ttl {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME_TTL = "包含过期时间的队列";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static void main(String[] args) throws IOException, TimeoutException {
        HashMap<String, Object> queueArgs = new HashMap<String, Object>();
        queueArgs.put("x-expires",10000);
        Connection connection = RabbitmqConnectFatory.getConnection("test");
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_TTL,true,false,false,queueArgs);
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,"测试包含过期时间的队列".getBytes(CHARSET_UTF8));
        channel.close();
    }
}
