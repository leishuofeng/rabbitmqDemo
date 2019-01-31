package com.study.shizhanzhinan.chapters_4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.shizhanzhinan.constant.RabbitmqConstant;
import com.study.shizhanzhinan.util.RabbitmqConnectFatory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 生产者在发送消息的时候如果不设置mandatory为true,为被匹配到的队列的消息将会丢失
 * 如果设置了mandatory为true,
 * 备份交换机，既不想复杂化生产者的编程逻辑，也不想丢失消息，那么可以使用交换机，
 * 可以将未被路由匹配到争取的队列中的消息储存在RabvbitMQ中再需要的时候去处理这些消息
 * 使用备份交换机存储那些未被分发成功的消息
 */
public class RabbitmqProducer4_alternate_exchange {
    private static final String EXCHANGE_NAME = "交换机_helloWord";
    private static final String EXCHANGE_NAME_BACKUP = "交换机备份_helloWord";
    private static final String ROUTING_KEY = "路由_helloWorld";
    private static final String QUEUE_NAME = "队列_helloWorld";
    private static final String QUEUE_NAME_BACKUP = "队列备份_helloWorld";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static void main(String[] args) throws IOException, TimeoutException {
        HashMap<String, Object> exchangeConditions = new HashMap<String, Object>();
        exchangeConditions.put("alternate-exchange",EXCHANGE_NAME_BACKUP);
        Connection connection = RabbitmqConnectFatory.getConnection("test");
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME_BACKUP, RabbitmqConstant.EXCHANGE_TYPE_FANOUT,true,false,null);
        channel.exchangeDeclare(EXCHANGE_NAME, RabbitmqConstant.EXCHANGE_TYPE_DIRECT,true,false,exchangeConditions);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME_BACKUP,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        channel.queueBind(QUEUE_NAME_BACKUP,EXCHANGE_NAME_BACKUP,ROUTING_KEY);
        channel.basicPublish(EXCHANGE_NAME,"test",false,null,"body test".getBytes(CHARSET_UTF8));
    }
}
