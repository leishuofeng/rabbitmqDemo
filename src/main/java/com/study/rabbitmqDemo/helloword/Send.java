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
			// 队列名字
			// 参数二，是否持久化队列，我们的队列模式是在内存中的，如果rabbitmq重启会丢失，如果我们设置为true会保存到erlang自带的数据库中
			// 参数三，是否排外，有两个作用，第一个当我们的链接关闭后是否会自动删除队列，作用二是否私有当前队列，如果私有了，其他通道不可访问的当前队列，一般适用于一个消费者的模式
			// 参数四 是否自动删除
			// 参数五 我们的一些其他参数
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
