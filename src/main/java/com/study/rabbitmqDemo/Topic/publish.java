package com.study.rabbitmqDemo.Topic;

import com.rabbitmq.client.Channel;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;

public class publish {
    // 交换机名字
    // 一条消息可以给多个消费者收到
    // 在交换机的基础上，在多一层路由的过滤
    private static final String EXCHANGE_NAME = "testbylsf_publish_subscribe_exchange_topic";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitConnectionUtil.initChannal();
        // 定义路由格式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        // 发布订阅模式，因为消息时发布到交换机中，交换机中是没有保存功能的，如果没有消费者，消息会丢失
        channel.basicPublish(EXCHANGE_NAME,"key.1",null,("   key1 topic消息").getBytes());
        channel.basicPublish(EXCHANGE_NAME,"key.2.2",null,("   key2 topic消息").getBytes());
        channel.basicPublish(EXCHANGE_NAME,"key.3.3",null,("   key3 topic消息").getBytes());
        channel.close();
    }
}
