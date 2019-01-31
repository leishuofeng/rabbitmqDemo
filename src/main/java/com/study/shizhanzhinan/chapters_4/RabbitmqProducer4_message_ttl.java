package com.study.shizhanzhinan.chapters_4;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;
import com.study.shizhanzhinan.constant.RabbitmqConstant;
import com.study.shizhanzhinan.util.RabbitmqConnectFatory;
import javafx.beans.binding.ObjectExpression;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 过期时间设置 TTL  time to live rabbitmq 可以对消息和队列设置ttl
 */
public class RabbitmqProducer4_message_ttl {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME = "过期队列_helloWorld";
    private static final String QUEUE_NAME_MESSAGE_TTL = "过期队列TTL_helloWorld";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqConnectFatory.getConnection("test");
        Channel channel = connection.createChannel();
        HashMap<String, Object> queueArgs = new HashMap<>();
        // 通过队列属性为消息设置ttl
        queueArgs.put("x-message-ttl",6000);
        // 前面已经为这个交换机设置过一次属性了，这一次再次设置会报错，所以将定义交换机的代码注释掉
        // channel.exchangeDeclare(EXCHANGE_NAME, RabbitmqConstant.EXCHANGE_TYPE_DIRECT,true,false,null);
        // 在定义的时候针对这个队列设置一个过期时间
        // 消息在队列中生存时间超过队列的过期时间，则成为死信（Dead Message）
        channel.queueDeclare(QUEUE_NAME,true,false,false,queueArgs);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);


        // 为每条消息设置过期时间
        channel.queueDeclare(QUEUE_NAME_MESSAGE_TTL,true,false,false,null);
        channel.queueBind(QUEUE_NAME_MESSAGE_TTL,EXCHANGE_NAME,ROUTING_KEY);
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        // 持久化消息
        properties.builder().deliveryMode(2);
        properties.builder().expiration("6000");
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,"测试过期队列".getBytes(CHARSET_UTF8));
        // 发送消息的时候，为消息本身设置过期时间
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,properties,"为消息设置过期时间".getBytes(CHARSET_UTF8));
    }
}
