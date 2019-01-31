package com.study.sw;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.shizhanzhinan.util.RabbitmqConnectFatory;

import java.io.IOException;

public class ProducerUtil {

    static private final String PRODUCER_NAME = "producer";

    static private Connection connection;

    static private Channel channel = null;

    static {
        try {
            connection = RabbitmqConnectFatory.getConnection("producer");
            channel = connection.createChannel();
            // 将信道开启事务
            channel.txSelect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean sendMessagetoRabbitmq(){

        return false;
    }
}
