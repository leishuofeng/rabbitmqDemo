package com.study.rabbitmqDemo.publishSubscribe;

import com.rabbitmq.client.Channel;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;

public class publish {
    // 交换机名字
    // 一条消息可以给多个消费者收到
    private static final String EXCHANGE_NAME = "testbylsf_publish_subscribe_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitConnectionUtil.initChannal();
        // 声明交换机 类型是fanout 也就是发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        // 发布订阅模式，因为消息时发布到交换机中，交换机中是没有保存功能的，如果没有消费者，消息会丢失
        for (int i = 0 ; i < 5 ;i++) {
            channel.basicPublish(EXCHANGE_NAME,"",null,(i+"发布订阅模式").getBytes());
        }
        channel.close();
    }
}
