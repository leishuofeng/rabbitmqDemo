package com.study.shizhanzhinan.chapters_4;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;
import com.study.shizhanzhinan.constant.RabbimqConstant;
import com.study.shizhanzhinan.util.RabbitMqConnectFatory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqProducer4 {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME = "队列_helloWorld";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqConnectFatory.getConnection("test");
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, RabbimqConstant.EXCHANGE_TYPE_DIRECT, true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        channel.basicPublish(EXCHANGE_NAME,
                ROUTING_KEY,
                true,
                null,
                "mandatory 参数为 true 时".getBytes(CHARSET_UTF8));
        // 生产消息到交换机时，mandatory 参数为 true 时，交换机无法根据自身类型
        // 和路由键找到一个符合条件的队列，那么RabbitMQ 会调用 Basic.Return 命令将消息
        // 返回给生产者，当 mandatory 参数为 false 出现上述情况直接丢弃消息
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("找不到 ： "+new String(body));
            }
        });
        channel.close();
    }
}
