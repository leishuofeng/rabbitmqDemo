/**
 * 
 */
package com.study.rabbitmqDemo.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName:  RabbitConnectionUtil   
 * @Description:TODO()   
 * @author: leishuofeng@aliyun.com 
 * @date:   2019年1月17日   
 */
public class RabbitConnectionUtil {
	
	public static Channel initChannal(){
		Connection connection = null;
		Channel channel = null;
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitmqPropertiesTools.getValue("rabbitmqConnectHost"));
			factory.setPort(Integer.parseInt(RabbitmqPropertiesTools.getValue("rabbitmqClientPort")));
			factory.setPassword(RabbitmqPropertiesTools.getValue("rabbitmqPassword"));
			factory.setUsername(RabbitmqPropertiesTools.getValue("rabbitmqUser"));
			connection = factory.newConnection();
			channel = connection.createChannel();
		}catch(Exception e){
			e.printStackTrace();
		}
		return channel;
	}
}
