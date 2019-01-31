package com.study.shizhanzhinan.chapters_1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * helloworld 消费者客户端代码
 */
public class RabbitmqProducer {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME = "队列_helloWorld";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitmqPropertiesTools.getValue("rabbitmqConnectHost"));
        factory.setPort(Integer.parseInt(RabbitmqPropertiesTools.getValue("rabbitmqClientPort")));
        factory.setPassword(RabbitmqPropertiesTools.getValue("rabbitmqPassword"));
        factory.setUsername(RabbitmqPropertiesTools.getValue("rabbitmqUser"));
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个 type = "direct"、持久化的，非自动删除的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        // 创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 将交换机和队列通过路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        // 发送一条持久化消息 hello world
        String message = "Hello World";
        // 通过路由将消息发送到与路由交换机绑定的队列中去
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,message.getBytes());
        channel.close();
        connection.close();
    }
    /**
     * 交换器类型介绍
     * fanout 他会把所有发送到该交换器的消息路由到所有与该及哦啊换气板顶的队列中
     * direct 类型的交换器路由规则也很简单，他会把消息路由到那些BindingKey和RoutingKey完全匹配的队列中
     * topic 与direct类型交换器相比，topic模式支持模糊路由配置，
     * headers 类型的交换器不依赖于路由键的匹配规则来路由消息，而是根据发送消息内容中的headers属性进行匹配。headers类型的交换器性能会很差，而且不适用，基本不会看到他的存在
     */
}
