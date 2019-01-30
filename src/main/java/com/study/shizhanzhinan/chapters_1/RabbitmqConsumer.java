package com.study.shizhanzhinan.chapters_1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitmqConsumer {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME = "队列_helloWorld";
    private static final String IP_ADDRESS = "";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS,PORT)
        };

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("");
        factory.setPassword("");
        // 这里的连接方式与生产者的demo有所不同
        // 创建链接
        Connection connection = factory.newConnection(addresses);
        // 创建信道
        final Channel channel = connection.createChannel();
        // 设置客户端最多接受违背ack的消息的个数
        channel.basicQos(64);
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                System.out.println(consumerTag);
                System.out.println("recv message: "+new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 使用消费者
        // 设置消费者是否自动确认接收消息，最好不要设置为自动接收，避免消息的丢失
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
        //等到回调函数执行完毕之后，关闭资源
        TimeUnit.SECONDS.sleep(6);
        channel.close();
        connection.close();
    }
}
