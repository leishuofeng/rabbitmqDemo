package com.study.shizhanzhinan.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.study.rabbitmqDemo.util.RabbitmqPropertiesTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author leishuofeng
 */
public class RabbitMqConnectFatory {
    static Logger log = LoggerFactory.getLogger(RabbitMqConnectFatory.class);

    private static Properties rabbitmqProperties = new Properties();

    static {
        try {
            rabbitmqProperties.load(RabbitmqPropertiesTools.class.getClassLoader().getResourceAsStream("rabbitmq.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 缓存连接工厂,将建立的链接放入map缓存
     */
    private static Map<String, ConnectionFactory> connectionFactoryMap = new HashMap<String, ConnectionFactory>();
    /**
     * 根据rabbitMqName获取一个连接，使用完记得要自己关闭连接 conn.close()
     */
    public static Connection getConnection(String rabbitMqName) {
        if(StringUtils.isEmpty(rabbitMqName)){
            log.error("rabbitMqName不能为空!");
            throw new NullPointerException("rabbitMqName为空");
        }
        if(connectionFactoryMap.get(rabbitMqName)==null){
            initConnectionFactory(rabbitMqName);
        }
        ConnectionFactory connectionFactory = connectionFactoryMap.get(rabbitMqName);
        if(connectionFactory==null){
            log.info("没有找到对应的rabbitmq,name={}",rabbitMqName);
        }
        try {
            return connectionFactory.newConnection();
        }catch (Exception e) {
            log.error("创建rabbitmq连接异常！",e);
            return null;
        }
    }
    /**
     * 初始化一个连接工厂
     * @param rabbitMqName
     */
    private static void initConnectionFactory(String rabbitMqName){

        try {
            ConnectionFactory factory = new ConnectionFactory();
            //factory.setAutomaticRecoveryEnabled(true);
            factory.setHost(rabbitmqProperties.getProperty("rabbitmqConnectHost"));
            factory.setPort(Integer.parseInt(rabbitmqProperties.getProperty("rabbitmqClientPort")));
            //factory.setVirtualHost(vhost);
            factory.setUsername(rabbitmqProperties.getProperty("rabbitmqUser"));
            factory.setPassword(rabbitmqProperties.getProperty("rabbitmqPassword"));
            connectionFactoryMap.put(rabbitMqName, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }
}
