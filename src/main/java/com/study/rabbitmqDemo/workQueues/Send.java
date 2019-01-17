/**
 * 
 */
package com.study.rabbitmqDemo.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.study.rabbitmqDemo.util.RabbitConnectionUtil;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;

/**   
 * @ClassName:  Send   
 * @Description:TODO()   
 * @author: leishuofeng@aliyun.com 
 * @date:   2019年1月17日   
 */
public class Send {
	
	private static final String QUEUE_NAME = "testbylsf-work_queue";
	
	public static void main(String[] args) {
		try {
			Channel channel = RabbitConnectionUtil.initChannal();
			channel.queueDeclare(QUEUE_NAME,false,false,false,null);
			for(int i = 0 ; i < 5 ;i++) {
				String message = String.join(" ", i+"");
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
