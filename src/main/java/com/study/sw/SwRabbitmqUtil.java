package com.study.sw;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.shizhanzhinan.util.RabbitmqConnectFatory;

import java.io.IOException;

public class SwRabbitmqUtil {

    static private final String CONNECTION_KEY = "SWConnection";

    static private Connection connection;

    static private Channel channel = null;

    static {
        try {
            connection = RabbitmqConnectFatory.getConnection(CONNECTION_KEY);
            channel = connection.createChannel();
            // 将信道开启事务
            channel.txSelect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean sendMessageToRabbitmqServer(){

        return false;
    }
}
