/**
 * 
 */
package com.study.rabbitmqDemo.helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;

/**   
 * @ClassName:  Recv   
 * @Description:TODO()   
 * @author: leishuofeng@aliyun.com 
 * @date:   2019年1月16日   
 */
public class Recv {
	
	private final static String QUEUE_NAME = "testbylsf-queue001";
	
	public static void main(String[] args) throws Exception {
		//不适用 try catch, 让程序 Cusumer 保持活跃才能接受消息
		Connection connection = null;
		Channel channel = null;
		// 1. create connection to the server(创建一个连接到rabbitmq服务器)
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitmqPropertiesTools.getValue("rabbitmqConnectHost"));
		factory.setPort(Integer.parseInt(RabbitmqPropertiesTools.getValue("rabbitmqClientPort")));
		factory.setUsername(RabbitmqPropertiesTools.getValue("rabbitmqUser"));
		factory.setPassword(RabbitmqPropertiesTools.getValue("rabbitmqPassword"));
		// 2. 通过连接工厂获取连接
		connection = factory.newConnection();
		// 3. 从connection中获取channel
		channel = connection.createChannel();
		// 4. 处理channel
		// 注意，我们在这里也声明了队列。因为我们可能在发布服务器之前启动使用者，
		// 所以我们希望在尝试使用来自它的消息之前确保队列存在。
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		DeliverCallback deliverCallback = (consumerTag,delivery) -> {
			String message = new String(delivery.getBody(),"UTF-8");
			System.out.println("Received " + message);
		};
		channel.basicConsume(QUEUE_NAME, true,deliverCallback,consumerTag  -> {});
	}
}
