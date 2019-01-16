/**
 * 
 */
package com.study.rabbitmqDemo.helloword;

import com.rabbitmq.client.ConnectionFactory;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**   
 * @ClassName:  Send   
 * @Description:TODO()   
 * @author: leishuofeng@aliyun.com 
 * @date:   2019年1月16日   
 */
public class Send {
	
	private final static String QUEUE_NAME = "testbylsf-queue001";
	
	public static void main(String[] args) throws Exception {
		Connection connection = null;
		Channel channel = null;
		try{
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
			channel.queueDeclare(QUEUE_NAME,false,false,false,null);
			String msg = "Hello World1";
			channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
			System.out.println("Sent "+msg);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				connection.close();
			}
		}
	}
}
