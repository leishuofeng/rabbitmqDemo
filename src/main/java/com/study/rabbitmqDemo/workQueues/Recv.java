/**
 * 
 */
package com.study.rabbitmqDemo.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;

/**   
 * @ClassName:  Recv   
 * @Description:TODO()   
 * @author: leishuofeng@aliyun.com 
 * @date:   2019年1月16日   
 */
public class Recv {
	
	private final static String QUEUE_NAME = "testbylsf-work_queue";
	
	public static void main(String[] args) throws Exception {
		Channel channel = RabbitConnectionUtil.initChannal();
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		DeliverCallback deliverCallback = (consumerTag,delivery) -> {
			String message = new String(delivery.getBody(),"UTF-8");
			System.out.println(" [x] Received '" + message + "'");
			try {
			    doWork(message);
			}catch(Exception e){
				e.printStackTrace();
			} finally {
			    System.out.println(" [x] Done");
			}
		};
		boolean autoAck = true; // acknowledgment is covered below
		channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
	}
	
	private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') {
				Thread.sleep(1000);
			}
	    }
	}
}
