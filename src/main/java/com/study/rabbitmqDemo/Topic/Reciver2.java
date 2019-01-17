package com.study.rabbitmqDemo.Topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;

import java.io.IOException;

public class Reciver2 {
    // 交换机名字
    private static final String EXCHANGE_NAME = "testbylsf_publish_subscribe_exchange_topic";
    // 队列名字
    private static final String QUEUE_NAME = "testbylsf_publish_subscribe_topic_QUEUE002";

    public static void main(String[] args) throws Exception{
        final Channel channel = RabbitConnectionUtil.initChannal();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key.#");
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者 222222222"+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 绑定消费者到队列中
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
